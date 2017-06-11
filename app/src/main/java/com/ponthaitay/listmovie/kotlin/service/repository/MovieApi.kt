package com.ponthaitay.listmovie.kotlin.service.repository

import com.ponthaitay.listmovie.kotlin.service.model.MovieDao
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie")
    fun getMovie(@Query("api_key") apiKey: String,
                 @Query("sort_by") sortBy: String,
                 @Query("page") page: Int): Observable<Response<MovieDao>>
}