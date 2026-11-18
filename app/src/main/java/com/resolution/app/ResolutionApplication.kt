package com.resolution.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ResolutionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
