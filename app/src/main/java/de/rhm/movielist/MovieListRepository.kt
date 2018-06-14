package de.rhm.movielist

import de.rhm.movielist.api.TMDbDate
import de.rhm.movielist.api.TheMovieDatabaseService
import de.rhm.movielist.api.model.MovieListResult
import io.reactivex.Single
import java.util.*

class MovieListRepository(private val service: TheMovieDatabaseService) {

    fun getMovies(releaseDate: Date, page: Int): Single<List<MovieListResult>> = service.discoverMovies(BuildConfig.THE_MOVIE_DATABASE_API_KEY, TMDbDate(releaseDate), page).map { it.movies }

}