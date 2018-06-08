package de.rhm.movielist.api

import com.squareup.moshi.Moshi
import org.koin.dsl.module.applicationContext

val ApiModule = applicationContext {

    bean { Moshi.Builder().add(DateAdapter).build() as Moshi }

}