package de.rhm.movielist

import android.arch.lifecycle.ViewModel
import de.rhm.movielist.api.model.MovieListResult
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class MovieListViewModel(repository: MovieListRepository): ViewModel() {

    private val uiAction = PublishSubject.create<FetchMovieListAction>()
    val uiState: Observable<out MovieListUiState> = uiAction
            //trigger fetch on ViewModel creation
            .startWith(FetchMovieListAction)
            //on every trigger prepare a new fetch
            .compose(ActionToState(repository, { uiAction.onNext(FetchMovieListAction) }))
            //cache last emitted ui state to preserve state on orientation change
            .replay(1)
            .autoConnect()
            .observeOn(AndroidSchedulers.mainThread())

}

object FetchMovieListAction

sealed class MovieListUiState
class Result(val movies: List<MovieListResult>) : MovieListUiState()
class Failure(val cause: Throwable, val retryAction: () -> Unit) : MovieListUiState()
object Loading : MovieListUiState()

class ActionToState(private val repository: MovieListRepository, private inline val retryAction: () -> Unit) : ObservableTransformer<FetchMovieListAction, MovieListUiState> {

    override fun apply(upstream: Observable<FetchMovieListAction>): ObservableSource<MovieListUiState> = upstream.switchMap {
        repository.getMovies().toObservable()
                //map success case
                .map<MovieListUiState> { Result(it) }
                //emit loading state while fetching
                .startWith(Loading)
                //emit failure state if fetch failed providing an action that triggers another fetch
                .onErrorReturn { Failure(it, retryAction) }
    }

}