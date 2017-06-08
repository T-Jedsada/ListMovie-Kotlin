package com.ponthaitay.listmovie.kotlin.movie

import android.arch.lifecycle.LifecycleObserver
import com.ponthaitay.listmovie.kotlin.api.MovieApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieModel : LifecycleObserver {

    private var movieApi: MovieApi
    private var nextPageAvailable: Boolean = true
    private val MAX_PAGE = 6
    private var page: Int = 1

    interface MovieModelCallback {
        fun requestMovieSuccess(data: MovieData?)
        fun requestMovieError(msg: String?)
    }

    init {
        val httpClient = OkHttpClient.Builder()
        addLoggingInterceptor(httpClient)
        movieApi = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/discover/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(MovieApi::class.java)
    }

    fun requestMovie(apiKey: String, sortBy: String, page: Int): Observable<Response<MovieDao>> =
            movieApi.getMovie(apiKey, sortBy, page).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn { Response.success(null) }

    fun requestMovie(apiKey: String, sortBy: String, callback: MovieModelCallback): Disposable =
            requestMovie(apiKey, sortBy, getNextPage()).subscribe({ checkResult(it, callback) },
                    { requestMovieError(callback, MovieData.retrieveMovieFailure().str) })

    fun nextPageAvailable() = nextPageAvailable

    private fun checkResult(response: Response<MovieDao>?, callback: MovieModelCallback) {
        val result = convertToMovieData(response)
        when (result) {
            is MovieData.Success -> {
                nextPageAvailable = page < MAX_PAGE
                callback.requestMovieSuccess(result)
            }
            is MovieData.Failure -> requestMovieError(callback, result.str)
        }
    }

    private fun requestMovieError(callback: MovieModelCallback, message: String?) {
        nextPageAvailable = false
        callback.requestMovieError(message)
    }

    private fun getNextPage(): Int = when {
        nextPageAvailable -> page++
        else -> 0
    }

    private fun convertToMovieData(response: Response<MovieDao>?): MovieData = when (response) {
        null -> MovieData.retrieveMovieFailure()
        else -> MovieData.retrieveMovieSuccess(response.body())
    }

    private fun addLoggingInterceptor(builder: OkHttpClient.Builder) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
    }
}