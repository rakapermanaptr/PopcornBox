package com.creospace.popcornbox

import android.app.Application
import com.creospace.utilities.logger.AppLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PopcornBoxApp: Application() {

    override fun onCreate() {
        super.onCreate()
        AppLogger.d(message = "Application is launched")
    }
}