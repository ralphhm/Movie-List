package de.rhm.movielist

import android.app.DatePickerDialog
import android.app.Dialog
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.v4.app.DialogFragment
import org.koin.android.architecture.ext.sharedViewModel
import java.util.*

class DatePickerFragment : DialogFragment() {

    private val dateFilterModel by sharedViewModel<DateFilterViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val (year, month, day) = dateFilterModel.selected.day
        return DatePickerDialog(activity, { _, y, m, d -> dateFilterModel.selected.filterAction.invoke(GregorianCalendar(y, m, d).time) }, year, month, day)
    }
}

class DateFilterViewModel : ViewModel() {
    lateinit var selected: DateFilter
}

class DateFilter(val day: Day, val filterAction: (Date) -> Unit)