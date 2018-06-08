package de.rhm.movielist.api

import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.Calendar.*

private const val JSON = "\"2018-06-06\""

class DateAdapterTest {

    @Test
    fun parse() = Moshi.Builder().add(DateAdapter).build().adapter(Date::class.java).fromJson(JSON)!!
            .let { Calendar.getInstance().apply { time = it } }
            .run {
                assertEquals(2018, get(YEAR))
                assertEquals(JUNE, get(MONTH))
                assertEquals(6, get(DAY_OF_MONTH))
            }

}