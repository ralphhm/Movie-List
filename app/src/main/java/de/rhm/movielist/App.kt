package de.rhm.movielist

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(listOf(RepositoryModule))
        Fresco.initialize(this);
    }
}