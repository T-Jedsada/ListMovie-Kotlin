package com.ponthaitay.listmovie.kotlin.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ponthaitay.listmovie.kotlin.di.module.ApplicationModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, Retrofit::class))
interface ApplicationComponent {
    fun app(): Application

    fun context(): Context

    fun preferences(): SharedPreferences
}
