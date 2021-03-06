package de.rhm.movielist.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class MovieListResult(@Json(name = "id") val id: String,
                           @Json(name = "title") val title: String,
                           @Json(name = "release_date") val release: Date,
                           @Json(name = "overview") val description: String,
                           @Json(name = "poster_path") val posterPath: String?,
                           @Json(name = "backdrop_path") val backdropPath: String?) {

    val posterUrl get() = "http://image.tmdb.org/t/p/w780/$posterPath"
    val backdropUrl get() = "http://image.tmdb.org/t/p/w154/$backdropPath"
}