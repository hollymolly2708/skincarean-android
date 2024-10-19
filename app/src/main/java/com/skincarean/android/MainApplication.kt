package com.skincarean.android

import android.app.Application
import com.skincarean.android.core.data.di.Injector

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}