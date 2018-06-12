package de.rhm.movielist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import de.rhm.movielist.api.model.MovieListResult
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.content_movie_list.*
import kotlinx.android.synthetic.main.item_movie.*
import org.koin.android.ext.android.inject
import java.text.DateFormat
import java.text.SimpleDateFormat

class MovieListActivity : AppCompatActivity() {

    private val repository: MovieListRepository by inject()
    private val section = Section()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        setSupportActionBar(toolbar)
        content.adapter = GroupAdapter<ViewHolder>().apply { add(section) }
        repository.getMovies().observeOn(AndroidSchedulers.mainThread()).subscribe { result -> section.update(result.map { MovieItem(it) })}
    }

}

class MovieItem(private val movie: MovieListResult): Item() {

    val format = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM)

    override fun bind(viewHolder: ViewHolder, position: Int) = with(viewHolder) {
        title.text = movie.title
        description.text = movie.description
        date.text = format.format(movie.release)
    }

    override fun getLayout() = R.layout.item_movie

}
