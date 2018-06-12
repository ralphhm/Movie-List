package de.rhm.movielist

import de.rhm.movielist.api.model.MovieListResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class MovieListViewModel(movieListRepository: MovieListRepository) {

    private val uiAction = PublishSubject.create<FetchMovieListAction>()
    val uiState: Observable<out MovieListUiState> = uiAction
            //trigger fetch on ViewModel creation
            .startWith(FetchMovieListAction)
            //on every trigger prepare a new fetch
            .switchMap {
                movieListRepository.getMovies().toObservable()
                        //map success case
                        .map<MovieListUiState> { Result(it) }
                        //emit loading state while fetching
                        .startWith(Loading)
                        //emit failure state if fetch failed providing an action that triggers another fetch
                        .onErrorReturn { Failure(it, { uiAction.onNext(FetchMovieListAction) }) }
            }
            .observeOn(AndroidSchedulers.mainThread())

}

object FetchMovieListAction

sealed class MovieListUiState
class Result(val movies: List<MovieListResult>) : MovieListUiState()
class Failure(val cause: Throwable, val retryAction: () -> Unit) : MovieListUiState()
object Loading : MovieListUiState()