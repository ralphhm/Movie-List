package de.rhm.movielist.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.text.DateFormat
import java.util.*

object DateAdapter: KoinComponent {

    private val dateFormat: ThreadLocal<DateFormat> by inject()

    @FromJson
    fun fromJson(date: String): Date = dateFormat.get().parse(date)

    @ToJson
    fun toJson(date: Date): String = throw NotImplementedError()

}