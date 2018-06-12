package de.rhm.movielist

import de.rhm.movielist.api.model.MovieListResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class MovieListViewModel(movieListRepository: MovieListRepository) {

    val uiState: Observable<out MovieListUiState> = movieListRepository.getMovies().toObservable().map { Result(it) as MovieListUiState}.startWith(Loading).observeOn(AndroidSchedulers.mainThread())

}

sealed class MovieListUiState
class Result(val movies: List<MovieListResult>) : MovieListUiState()
object Loading : MovieListUiState()