package de.rhm.movielist.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DiscoverMovieResponse(@Json(name = "results") val movies: List<MovieListResult>)