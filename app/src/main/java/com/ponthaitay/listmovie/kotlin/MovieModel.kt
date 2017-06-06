package com.ponthaitay.listmovie.kotlin

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

class MovieModel {

    private object Instance {
        val INSTANCE = MovieModel()
    }

    companion object {
        val instance: MovieModel by lazy { Instance.INSTANCE }
    }

    interface MovieModelCallback {
        fun getMovieSuccess(data: MovieData?)
        fun getMovieComplete()
        fun getMovieError(msg: String?)
    }

    private var movieApi: MovieApi
    private var nextPageAvailable: Boolean = true
    private var page: Long = 1

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

    fun requestMovie(apiKey: String, sortBy: String, page: Long): Observable<Response<MovieData>> =
            movieApi.getMovie(apiKey, sortBy, page).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn { retrieveMovieFailure() }

    fun requestMovie(apiKey: String, sortBy: String, callback: MovieModelCallback): Disposable =
            requestMovie(apiKey, sortBy, getNextPage()).subscribe({ checkResult(it, callback) },
                    { requestMovieError(callback, it.message) })

    fun nextPageAvailable(): Boolean = nextPageAvailable

    private fun requestMovieError(callback: MovieModelCallback, message: String?) {
        nextPageAvailable = false
        callback.getMovieError(message)
    }

    private fun getNextPage(): Long {
        if (nextPageAvailable) return page++
        else return 1
    }

    private fun checkResult(response: Response<MovieData>?, callback: MovieModelCallback) {
        val result = response?.body()
        when (result) {
            is Success -> {
                nextPageAvailable = page < 10
                if (nextPageAvailable) callback.getMovieSuccess(result)
                else callback.getMovieComplete()
            }
            is Failure -> requestMovieError(callback, result.str)
            else -> requestMovieError(callback, response?.message())
        }
    }

    private fun addLoggingInterceptor(builder: OkHttpClient.Builder) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
    }
}