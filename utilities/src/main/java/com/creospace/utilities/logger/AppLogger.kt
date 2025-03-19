package com.creospace.utilities.logger

import android.util.Log
import com.creospace.utilities.BuildConfig

object AppLogger {

    private const val TAG = "AppLogger"

    fun d(tag: String = TAG, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }

    fun e(tag: String = TAG, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, message)
        }
    }
}