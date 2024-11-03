package com.skincarean.android.ui

import android.app.Application
import com.skincarean.android.Utilities

class SkincareanApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Utilities.clearGlideCache(this)
    }
}