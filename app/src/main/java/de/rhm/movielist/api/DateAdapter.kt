package de.rhm.movielist.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

object DateAdapter {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    @FromJson
    fun fromJson(date: String): Date = dateFormat.parse(date)

    @ToJson
    fun toJson(date: Date): String = throw NotImplementedError()

}