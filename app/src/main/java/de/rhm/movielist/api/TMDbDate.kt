package de.rhm.movielist.api

import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.text.DateFormat
import java.util.*

class TMDbDate(val date: Date): KoinComponent {

    private val dateFormat: ThreadLocal<DateFormat> by inject()

    override fun toString() = dateFormat.get().format(date)
}