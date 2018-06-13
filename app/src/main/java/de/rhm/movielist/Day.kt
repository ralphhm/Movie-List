package de.rhm.movielist

import java.util.*
import java.util.Calendar.*

data class Day(val year: Int, val month: Int, val dayOfMonth: Int)

fun Date.toDay() = Calendar.getInstance().apply { time = this@toDay }.run { Day(get(YEAR), get(MONTH), get(DAY_OF_MONTH)) }