package de.rhm.movielist.api

import de.rhm.movielist.RepositoryModule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.AutoCloseKoinTest
import java.util.*

class TMDbDateTest: AutoCloseKoinTest() {

    @Before
    fun before() {
        startKoin(listOf(RepositoryModule))
    }

    @Test
    fun toString_returnsFormattedDate() {
        val date = Calendar.getInstance().apply { set(2018, Calendar.JUNE, 7)}.time
        assertEquals("2018-06-07", TMDbDate(date).toString())
    }
}