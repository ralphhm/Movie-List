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
            .compose(ActionToState({action -> repository.getMovies(action.releasedUntil)}, { action -> uiAction.onNext(action) }))
            //cache last emitted ui state to preserve state on orientation change
            .replay(1)
            .autoConnect()
            .observeOn(AndroidSchedulers.mainThread())

}

class FetchMovieListAction(val releasedUntil: Date = Date())

sealed class MovieListUiState
class Result(val movies: MovieList, val filterDate: Date, val filterAction: (Date) -> Unit) : MovieListUiState()
class Failure(val cause: Throwable, val retryAction: () -> Unit) : MovieListUiState()
object Loading : MovieListUiState()

class ActionToState(private inline val call: (FetchMovieListAction) -> Single<MovieList>, private inline val retryAction: (FetchMovieListAction) -> Unit) : ObservableTransformer<FetchMovieListAction, MovieListUiState> {

    override fun apply(upstream: Observable<FetchMovieListAction>): ObservableSource<MovieListUiState> = upstream.switchMap { action ->
        call.invoke(action)
                //map success case
                .map<MovieListUiState> { Result(it, action.releasedUntil, {date -> retryAction.invoke(FetchMovieListAction(date))}) }
                //emit loading state while fetching
                .toObservable().startWith(Loading)
                //emit failure state if fetch failed providing an action that triggers another fetch
                .onErrorReturn { Failure(it, { retryAction.invoke(action) }) }
    }

}