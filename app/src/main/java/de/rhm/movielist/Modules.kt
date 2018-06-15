package de.rhm.movielist

import com.squareup.moshi.Moshi
import de.rhm.movielist.api.DateAdapter
import de.rhm.movielist.api.TheMovieDatabaseService
import de.rhm.movielist.movie.SelectedMovie
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

val RepositoryModule = applicationContext {

    factory { SimpleDateFormat("yyyy-MM-dd", Locale.US) as DateFormat }

    bean {
        object : ThreadLocal<DateFormat>() {
            override fun initialValue(): DateFormat = get(null)
        } as ThreadLocal<DateFormat>
    }

    bean { Moshi.Builder().add(DateAdapter).build() as Moshi }

    bean { MoshiConverterFactory.create(get()) as Converter.Factory }

    bean { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) as Interceptor }

    bean { OkHttpClient.Builder().addInterceptor(get()).build() as OkHttpClient }

    bean {
        Retrofit.Builder()
                .addConverterFactory(get())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl("https://api.themoviedb.org/3/")
                .client(get())
                .build()
                .create(TheMovieDatabaseService::class.java) as TheMovieDatabaseService
    }

    bean { MovieListRepository(get()) }

    bean { MovieListViewModel(get()) }

    bean { DateFilterViewModel() }

    bean { SelectedMovie() }
}