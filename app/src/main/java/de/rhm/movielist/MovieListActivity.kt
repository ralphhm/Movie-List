package de.rhm.movielist

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.koin.android.ext.android.inject

class MovieListActivity : AppCompatActivity() {

    private val repository: MovieListRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        setSupportActionBar(toolbar)
        repository.getMovies().observeOn(AndroidSchedulers.mainThread()).subscribe { result -> Snackbar.make(app_bar, "${result.size} movies", Snackbar.LENGTH_LONG).show()}
    }

}
