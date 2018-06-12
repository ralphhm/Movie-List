package de.rhm.movielist.api

import com.squareup.moshi.Moshi
import de.rhm.movielist.RepositoryModule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import java.util.*
import java.util.Calendar.*

private const val JSON = "\"2018-06-06\""

class DateAdapterTest : AutoCloseKoinTest() {

    val moshi: Moshi by inject()

    @Before
    fun before() {
        startKoin(listOf(RepositoryModule))
    }

    @Test
    fun parse() = moshi.adapter(Date::class.java).fromJson(JSON)!!
            .let { Calendar.getInstance().apply { time = it } }
            .run {
                assertEquals(2018, get(YEAR))
                assertEquals(JUNE, get(MONTH))
                assertEquals(6, get(DAY_OF_MONTH))
            }

}