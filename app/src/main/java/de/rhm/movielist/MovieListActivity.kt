package de.rhm.movielist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import de.rhm.movielist.api.model.MovieListResult
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.content_movie_list.*
import kotlinx.android.synthetic.main.item_movie.*
import org.koin.android.ext.android.inject
import java.text.DateFormat
import java.text.SimpleDateFormat

class MovieListActivity : AppCompatActivity() {

    private val viewModel: MovieListViewModel by inject()
    private val section = Section()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        setSupportActionBar(toolbar)
        content.adapter = GroupAdapter<ViewHolder>().apply { add(section) }
        viewModel.uiState.subscribe { state -> bind(state) }.let { disposable.add(it) }
    }

    private fun bind(uiState: MovieListUiState) = when (uiState) {
        Loading -> section.update(listOf(LoadingContentItem))
        is Failure -> section.update(listOf(ErrorItem(uiState.retryAction)))
        is Result -> section.update(uiState.movies.map { MovieItem(it) })
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}

class MovieItem(private val movie: MovieListResult) : Item() {

    val format = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM)

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
