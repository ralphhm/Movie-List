package de.rhm.movielist.api

import de.rhm.movielist.api.model.DiscoverMovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDatabaseService {

    @GET("discover/movie?sort_by=primary_release_date.desc")
    fun discoverMovies(
            @Query("api_key") apiKey: String,
            @Query("primary_release_date.lte") latestRelease: TMDbDate,
            @Query("page") page: Int
    ): Single<DiscoverMovieResponse>
}