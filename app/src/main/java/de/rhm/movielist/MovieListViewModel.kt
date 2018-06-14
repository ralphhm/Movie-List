package de.rhm.movielist

import android.arch.lifecycle.ViewModel
import de.rhm.movielist.api.model.MovieListResult
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.*

typealias MovieList = List<MovieListResult>

class MovieListViewModel(repository: MovieListRepository) : ViewModel() {

    private val uiAction = PublishSubject.create<FetchMovieListAction>()
    val uiState: Observable<out MovieListUiState> = uiAction
            //trigger fetch on ViewModel creation
            .startWith(FetchMovieListAction())
            //on every trigger prepare a new fetch
            .compose(ActionToState({ action -> repository.getMovies(action.filterDate, action.page) }, { action -> uiAction.onNext(action) }))
            .distinctUntilChanged()
            .scan { previous, current ->
                when {
                //when Result is followed by Loading with the same date
                    previous is Result && current is Loading && previous.filterDate == current.filterDate -> LoadingMore(previous)
                //when LoadingMore is followed by a successful Result, movie lists need to be aggregated
                    previous is LoadingMore && current is Result -> current.copy(previous.lastResult.movies + current.movies)
                //when LoadingMore is followed by Failure fallback to previous successful Result
                    previous is LoadingMore && current is Failure -> previous.lastResult
                    else -> current
                }
            }
            //cache last emitted ui state to preserve state on orientation change
            .replay(1)
            .autoConnect()
            .observeOn(AndroidSchedulers.mainThread())
}

data class FetchMovieListAction(val filterDate: Date = Date(), val page: Int = 1)

sealed class MovieListUiState
data class Result(val movies: MovieList, val filterDate: Date, val filterAction: (Date) -> Unit, val loadMoreAction: () -> Unit) : MovieListUiState()
class Failure(val cause: Throwable, val retryAction: () -> Unit) : MovieListUiState()
data class Loading(val filterDate: Date) : MovieListUiState()
class LoadingMore(val lastResult: Result) : MovieListUiState()

class ActionToState(
        private inline val call: (FetchMovieListAction) -> Single<MovieList>,
        private inline val nextAction: (FetchMovieListAction) -> Unit
) : ObservableTransformer<FetchMovieListAction, MovieListUiState> {

    override fun apply(upstream: Observable<FetchMovieListAction>): ObservableSource<MovieListUiState> {
        return upstream.switchMap { action ->
            call.invoke(action)
                    //map success case
                    .map<MovieListUiState> { Result(it, action.filterDate, { date -> nextAction.invoke(FetchMovieListAction(date)) }, { nextAction.invoke(action.copy(page = action.page + 1)) }) }
                    //emit loading state while fetching
                    .toObservable().startWith(Loading(action.filterDate))
                    //emit failure state if fetch failed providing an action that triggers another fetch
                    .onErrorReturn { Failure(it) { nextAction.invoke(action) } }
        }
    }

}