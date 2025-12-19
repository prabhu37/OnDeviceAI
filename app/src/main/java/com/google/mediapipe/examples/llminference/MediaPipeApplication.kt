package com.google.mediapipe.examples.llminference

import android.app.Application
import com.google.mediapipe.examples.llminference.di.AppModule
import com.google.mediapipe.examples.llminference.data.ObjectBoxStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MediaPipeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MediaPipeApplication)
            modules(AppModule().module)

        }
       ObjectBoxStore.init(this)
    }
}
