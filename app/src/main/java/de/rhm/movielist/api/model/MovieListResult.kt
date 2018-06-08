package de.rhm.movielist.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
class MovieListResult(@Json(name = "title") val title: String,
                      @Json(name = "release_date") val release: Date,
                      @Json(name = "overview") val description: String,
                      @Json(name = "poster_path") val posterPath: String?)