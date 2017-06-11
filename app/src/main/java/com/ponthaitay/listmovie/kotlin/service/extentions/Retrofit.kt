package com.ponthaitay.listmovie.kotlin.service.extentions

import com.ponthaitay.listmovie.kotlin.service.repository.MovieApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun providesMovieAPIs(): MovieApi {
    return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/discover/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(providesOkHttpClient().build())
            .build()
            .create(MovieApi::class.java)
}

fun providesOkHttpClient(): OkHttpClient.Builder {
    val httpClient = OkHttpClient.Builder()
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    httpClient.addInterceptor(httpLoggingInterceptor)
    return httpClient
}