package de.rhm.movielist.movie

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.rhm.movielist.R
import de.rhm.movielist.api.model.MovieListResult
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_details.*
import org.koin.android.ext.android.inject

class MovieDetailsActivity : AppCompatActivity() {

    private val selectedMovie: SelectedMovie by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        selectedMovie.observe(this, Observer {
            bind(it!!)
        })
    }

    private fun bind(movie: MovieListResult) {
        image.setImageURI(movie.posterUrl)
        supportActionBar?.title = movie.title
    }

}
