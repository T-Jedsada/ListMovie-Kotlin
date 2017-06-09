package com.ponthaitay.listmovie.kotlin

import android.app.Application
import com.ponthaitay.listmovie.kotlin.di.ApplicationComponent

class MyApplication : Application() {

    companion object {
        @JvmStatic lateinit var instance: MyApplication
        @JvmStatic lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerApplicationComponent.create()
    }
}