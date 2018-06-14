package de.rhm.movielist

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import de.rhm.movielist.api.model.MovieListResult
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.content_movie_list.*
import kotlinx.android.synthetic.main.item_filter_date.*
import kotlinx.android.synthetic.main.item_movie.*
import org.koin.android.architecture.ext.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.getInstance
import kotlin.math.absoluteValue

class MovieListActivity : AppCompatActivity() {

    private val viewModel by viewModel<MovieListViewModel>()
    private val section = Section()
    private val disposable = CompositeDisposable()
    private val filterViewModel by viewModel<DateFilterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        setSupportActionBar(toolbar)
        content.adapter = GroupAdapter<ViewHolder>().apply { add(section) }
        viewModel.uiState.subscribe { state -> bind(state) }.let { disposable.add(it) }
    }

    private fun bind(uiState: MovieListUiState) = when (uiState) {
        is Loading -> section.update(listOf(LoadingContentItem))
        is LoadingMore -> section.update(uiState.lastResult.movies.map { MovieItem(it) } + LoadingMoreItem)
        is Failure -> section.update(listOf(ErrorItem(uiState.retryAction)))
        is Result -> {
            filterViewModel.selected = DateFilter(uiState.filterDate.toDay(), uiState.filterAction)
            section.update(listOf(FilterDateItem(uiState.filterDate, supportFragmentManager)) + uiState.movies.map { MovieItem(it) } + LoadMoreOnBindItem(uiState.loadMoreAction))
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}

data class MovieItem(private val movie: MovieListResult) : Item(movie.id.hashCode().toLong().absoluteValue) {

    private val format = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM)

    override fun bind(viewHolder: ViewHolder, position: Int) = with(viewHolder) {
        title.text = movie.title
        description.text = movie.description
        date.text = format.format(movie.release)
    }

    override fun getLayout() = R.layout.item_movie

}

object LoadingContentItem : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) = Unit
    override fun getLayout() = R.layout.item_loading_content
}

class ErrorItem(private val retryAction: () -> Unit) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) = viewHolder.itemView.setOnClickListener { retryAction.invoke() }
    override fun getLayout() = R.layout.item_error
}

class FilterDateItem(date: Date, private val fragmentManager: FragmentManager) : Item() {

    private val cal = getInstance().apply { time = date }
    private val formatter = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM)

    override fun getLayout() = R.layout.item_filter_date

    override fun bind(viewHolder: ViewHolder, position: Int) = with(viewHolder) {
        filter_date.text = itemView.context.getString(R.string.released_until, formatter.format(cal.time))
        action_change.setOnClickListener {
            DatePickerFragment().show(fragmentManager, "datePicker")
        }
    }
}

object LoadingMoreItem : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) = Unit
    override fun getLayout() = R.layout.item_loading_more
}

class LoadMoreOnBindItem(private val loadMoreAction: () -> Unit) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        loadMoreAction.invoke()
    }

    override fun getLayout() = R.layout.item_empty

}
