package de.rhm.movielist.api.model

import com.squareup.moshi.Moshi
import de.rhm.movielist.api.DateAdapter
import org.junit.Assert.*
import org.junit.Test

private const val JSON_RESPONSE = """
{
    "page": 1,
    "total_results": 335141,
    "total_pages": 16758,
    "results": [
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
        },
        {
            "vote_count": 0,
            "id": 499558,
            "video": false,
            "vote_average": 0,
            "title": "Infinite Football",
            "popularity": 1.953002,
            "poster_path": "/zblte8nw9FnpE8r0dedQwfAx3B5.jpg",
            "original_language": "ro",
            "original_title": "Fotbal infinit",
            "genre_ids": [
                99
            ],
            "backdrop_path": null,
            "adult": false,
            "overview": "They talk about the beautiful game, but for Laurențiu Ginghină, it’s not enough. Football must be modified, streamlined, freed from restraints; corners are to be rounded off, players assigned to zones and subteams, norms revised.",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 486606,
            "video": false,
            "vote_average": 0,
            "title": "Warfighter",
            "popularity": 17.095247,
            "poster_path": "/7fKtVXA56ZHDMcx41OXtR5YzAX6.jpg",
            "original_language": "en",
            "original_title": "Warfighter",
            "genre_ids": [],
            "backdrop_path": null,
            "adult": false,
            "overview": "Rusty Wittenburg is a Navy SEAL struggling to balance his family life and his job. He fights daily to maintain the line between reality and the nightmares his PTSD conjures up for him. Dedicated to his team and his mission, he is willing to give the ultimate sacrifice for his fellow brothers and teammates.",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 1,
            "id": 478795,
            "video": false,
            "vote_average": 0,
            "title": "El Libro de Lila",
            "popularity": 10.350263,
            "poster_path": "/hwHCLQW258GCQEw2emhEnzzvVQJ.jpg",
            "original_language": "es",
            "original_title": "El Libro de Lila",
            "genre_ids": [],
            "backdrop_path": null,
            "adult": false,
            "overview": "",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 486468,
            "video": false,
            "vote_average": 0,
            "title": "Riga (Take One)",
            "popularity": 1.221169,
            "poster_path": null,
            "original_language": "lv",
            "original_title": "Riga (Take One)",
            "genre_ids": [],
            "backdrop_path": null,
            "adult": false,
            "overview": "Riga, Latvia. Four women: Elita, a passioned actress, Elina, her daughter, Iveta, a tourist guide and Paulina, a teenage ballet dancer. All are in love and going through strong emotions.  A free-style composition about passion and arts, a visually stunning cinematic jazz partition.",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 466703,
            "video": false,
            "vote_average": 0,
            "title": "Volontaire",
            "popularity": 15.395878,
            "poster_path": "/ch9MARSVGiPiBsBcR4bMbMHcraf.jpg",
            "original_language": "fr",
            "original_title": "Volontaire",
            "genre_ids": [
                18
            ],
            "backdrop_path": null,
            "adult": false,
            "overview": "The story revolves around 22-year-old Laure, who is trying to find her feet. After performing brilliantly in her literature studies, she enrols as a communications officer in the Naval Fusiliers. She will quickly have to adapt to and assimilate the rules that apply within the institution. But Laure is a determined woman, and she has a thirst for knowledge – still. A thirst to learn to get to know and be comfortable with herself, and to find her place.",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 517731,
            "video": false,
            "vote_average": 0,
            "title": "Three Faces",
            "popularity": 11.472661,
            "poster_path": "/d6d2EHyH1wDii1BCY5E2Dg5Eodw.jpg",
            "original_language": "fa",
            "original_title": "سه رخ",
            "genre_ids": [
                18
            ],
            "backdrop_path": "/7IVv9pC0KEYH40GsryFJvwYhQlq.jpg",
            "adult": false,
            "overview": "The stories of three Iranian actresses: one from pre-revolution days who had to stop acting, one popular star of today known throughout the country and one young girl longing to attend a drama conservatory.",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 515162,
            "video": false,
            "vote_average": 0,
            "title": "Bite",
            "popularity": 10.890406,
            "poster_path": "/ox0xpgtjjkU6pisjYcMbnITWCLN.jpg",
            "original_language": "en",
            "original_title": "Bite",
            "genre_ids": [
                18,
                27
            ],
            "backdrop_path": null,
            "adult": false,
            "overview": "Pursued by a dangerous criminal after a failed theft, con artists Nina and Yaz get more than they bargained for when they target a seemingly innocent elderly widow.",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 514933,
            "video": false,
            "vote_average": 0,
            "title": "Russian Psycho",
            "popularity": 1.007168,
            "poster_path": "/vwXdu9HYrrjEFk7EnULcYi7yNvp.jpg",
            "original_language": "ru",
            "original_title": "Русский Бес",
            "genre_ids": [
                53,
                35,
                18
            ],
            "backdrop_path": null,
            "adult": false,
            "overview": "The film’s action happens in today’s Russia, in Moscow. A young man, Sviatoslav Ivanov, is in love with the young girl Asya and decides to marry her. By training he is a graphic designer, but Asya’s father is the managing director of a large Russian bank. Sviatoslav decides to start his own business and open a restaurant to create for Asya the living conditions she is accustomed to. A number of serious obstacles suddenly appear in the way of the young man and menace his life... He finds out that he has a secret ill-wisher who wants to ruin him and his business. The young man goes to the extreme, gradually untangling the secret plot... At the end of the story Sviatoslav manages to answer the question about his enemy...",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 516617,
            "video": false,
            "vote_average": 0,
            "title": "Been Busy",
            "popularity": 10.238529,
            "poster_path": null,
            "original_language": "en",
            "original_title": "Been Busy",
            "genre_ids": [
                18
            ],
            "backdrop_path": null,
            "adult": false,
            "overview": "It's time to join the others. The brother returns home. The sister tries to move forward. A fiction film from Jhon Hernandez.",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 513629,
            "video": false,
            "vote_average": 0,
            "title": "Deep Rivers",
            "popularity": 10.736794,
            "poster_path": null,
            "original_language": "ru",
            "original_title": "Глубокие реки",
            "genre_ids": [
                18
            ],
            "backdrop_path": "/pmpem1YajDSlZ112SDR3JgdcZL1.jpg",
            "adult": false,
            "overview": "The youngest son returns home to help his father and brothers meet the targets for a profitable order on cut wood. During his absence nothing has changed: the same back-breaking toil for the sake of a piece of bread, the same resistance from the neighbouring settlement, the same inability of the family members to express their love and understanding for their kin. And the river, which may rise at any time and wash away the parental home that stands on its banks.",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 501303,
            "video": false,
            "vote_average": 0,
            "title": "Pacific Angels",
            "popularity": 11.102404,
            "poster_path": "/hiShMXAKy36vRHjl7aTNgOPNHJM.jpg",
            "original_language": "en",
            "original_title": "Pacific Angels",
            "genre_ids": [
                18,
                9648
            ],
            "backdrop_path": null,
            "adult": false,
            "overview": "Ethan, a film student in the Pacific Northwest, finds himself reflecting on his family estrangement when he takes interest in claims of an alien abduction at a local beach.",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 526318,
            "video": false,
            "vote_average": 0,
            "title": "La Légende",
            "popularity": 13.255842,
            "poster_path": "/G0JyxxaCDnHpP9aW4AnTrt46QS.jpg",
            "original_language": "fr",
            "original_title": "La Légende",
            "genre_ids": [
                18
            ],
            "backdrop_path": null,
            "adult": false,
            "overview": "",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 524713,
            "video": false,
            "vote_average": 0,
            "title": "Casting",
            "popularity": 11.084473,
            "poster_path": null,
            "original_language": "ru",
            "original_title": "Кастинг",
            "genre_ids": [],
            "backdrop_path": null,
            "adult": false,
            "overview": "A mother brings her daughter to a casting session. Standing before strangers, the girl freezes up and can’t say a word. The mother puts pressure on the girl, who remains silent. In a rage, the mother takes Anya out of the room. Afraid of the mother’s anger, Anya will try once again, but now Anya’s horror is even bigger and, bursting into tears, she runs away. While the mother apologizes for the daughter’s behaviour, Anya gets lost in the corridors of the film studio. The mother searches for her, but the girl is nowhere to be found. In despair, the woman runs along a range of corridors, opens door after door...",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 528409,
            "video": false,
            "vote_average": 0,
            "title": "La Nuit",
            "popularity": 3,
            "poster_path": null,
            "original_language": "fr",
            "original_title": "La Nuit",
            "genre_ids": [],
            "backdrop_path": null,
            "adult": false,
            "overview": "",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 528411,
            "video": false,
            "vote_average": 0,
            "title": "The Other Munch",
            "popularity": 3,
            "poster_path": null,
            "original_language": "no",
            "original_title": "The Other Munch",
            "genre_ids": [
                99
            ],
            "backdrop_path": null,
            "adult": false,
            "overview": "",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 516518,
            "video": false,
            "vote_average": 0,
            "title": "WWN Supershow: Mercury Rising 2018",
            "popularity": 10.226323,
            "poster_path": "/zKgvVAKYzXLxz4KiPMo29yXaxdR.jpg",
            "original_language": "en",
            "original_title": "WWN Supershow: Mercury Rising 2018",
            "genre_ids": [],
            "backdrop_path": "/6UOTr4Wks9LbReuKfT0fvWT3x0m.jpg",
            "adult": false,
            "overview": "The modern incarnation of the Mercury Rising WWN Supershow has become an event filled with dream matches year after year. The event has showcased talents from nearly every WWN brand since 2015.",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 520924,
            "video": false,
            "vote_average": 0,
            "title": "Uncle Sasha",
            "popularity": 3.401221,
            "poster_path": null,
            "original_language": "ru",
            "original_title": "Дядя Саша",
            "genre_ids": [
                35
            ],
            "backdrop_path": null,
            "adult": false,
            "overview": "An almost Chekhovian story about a once successful director making his last film, in spite of everything and everybody, in his country house. Strange guests, vain actors, somewhat eccentric neighbours and the director are reason enough for the author’s irony, and sometimes sarcasm. And you remember? Chekhov’s guns always fire!",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 527461,
            "video": false,
            "vote_average": 0,
            "title": "Unterwerfung",
            "popularity": 6.488,
            "poster_path": null,
            "original_language": "de",
            "original_title": "Unterwerfung",
            "genre_ids": [],
            "backdrop_path": "/yDGzfsb7S8aGRZcCZ0ZnwFCRirc.jpg",
            "adult": false,
            "overview": "",
            "release_date": "2018-06-06"
        },
        {
            "vote_count": 0,
            "id": 528534,
            "video": false,
            "vote_average": 0,
            "title": "snoooooooooooooozeeeeeeeeee",
            "popularity": 3,
            "poster_path": null,
            "original_language": "en",
            "original_title": "snoooooooooooooozeeeeeeeeee",
            "genre_ids": [
                10770
            ],
            "backdrop_path": null,
            "adult": false,
            "overview": "Found footage film by Josiah Morgan.",
            "release_date": "2018-06-06"
        }
    ]
}
"""

class DiscoverMovieResponseTest {

    @Test
    fun parse() = Moshi.Builder().add(DateAdapter).build().adapter(DiscoverMovieResponse::class.java).fromJson(JSON_RESPONSE)!!.run {
        assertEquals(12, movies.size)
    }
}