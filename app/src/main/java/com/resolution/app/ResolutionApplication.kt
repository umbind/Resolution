package com.resolution.app

import android.app.Application

class ResolutionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize logging and offline crash reporting hooks if needed
    }
}
