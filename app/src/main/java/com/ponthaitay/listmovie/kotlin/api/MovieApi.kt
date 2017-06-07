package com.ponthaitay.listmovie.kotlin.api

import com.ponthaitay.listmovie.kotlin.movie.MovieDao
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie")
    fun getMovie(@Query("api_key") apiKey: String,
                 @Query("sort_by") sortBy: String,
                 @Query("page") page: Long): Observable<Response<MovieDao>>
}