package de.rhm.movielist

import de.rhm.movielist.api.model.MovieListResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class MovieListViewModel(movieListRepository: MovieListRepository) {

    val uiState: Observable<out MovieListUiState> = movieListRepository.getMovies().toObservable().map { Result(it) as MovieListUiState}.onErrorReturn { Failure(it, {}) }.startWith(Loading).observeOn(AndroidSchedulers.mainThread())

}

sealed class MovieListUiState
class Result(val movies: List<MovieListResult>) : MovieListUiState()
class Failure(val cause: Throwable, val retryAction: () -> Unit) : MovieListUiState()
object Loading : MovieListUiState()