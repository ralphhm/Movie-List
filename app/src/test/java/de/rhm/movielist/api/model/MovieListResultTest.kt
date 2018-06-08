package de.rhm.movielist.api.model

import com.squareup.moshi.Moshi
import de.rhm.movielist.api.DateAdapter
import org.junit.Assert.*
import org.junit.Test

private const val JSON_RESPONSE = """
{
    "vote_count": 61,
    "id": 351286,
    "video": false,
    "vote_average": 7.3,
    "title": "Jurassic World: Fallen Kingdom",
    "popularity": 239.047643,
    "poster_path": "/c9XxwwhPHdaImA2f1WEfEsbhaFB.jpg",
    "original_language": "en",
    "original_title": "Jurassic World: Fallen Kingdom",
    "genre_ids": [
        28,
        12,
        878
    ],
    "backdrop_path": "/gBmrsugfWpiXRh13Vo3j0WW55qD.jpg",
    "adult": false,
    "overview": "A volcanic eruption threatens the remaining dinosaurs on the island of Isla Nublar, where the creatures have freely roamed for several years after the demise of an animal theme park known as Jurassic World. Claire Dearing, the former park manager, has now founded the Dinosaur Protection Group, an organization dedicated to protecting the dinosaurs. To help with her cause, Claire has recruited Owen Grady, a former dinosaur trainer who worked at the park, to prevent the extinction of the dinosaurs once again.",
    "release_date": "2018-06-06"
}
"""

class MovieListResultTest {

    @Test
    fun parse() = Moshi.Builder().add(DateAdapter).build().adapter(MovieListResult::class.java).fromJson(JSON_RESPONSE)!!.run {
        assertEquals("Jurassic World: Fallen Kingdom", title)
    }

}