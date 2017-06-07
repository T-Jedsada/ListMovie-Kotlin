package com.ponthaitay.listmovie.kotlin.movie

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

class MovieModel {

    private var movieApi: MovieApi
    private var nextPageAvailable: Boolean = true
    private var page: Long = 1

    private object Instance {
        val INSTANCE = MovieModel()
    }

    companion object {
        val instance: MovieModel by lazy { MovieModel.Instance.INSTANCE }
    }

    interface MovieModelCallback {
        fun getMovieSuccess(data: MovieData?)
        fun getMovieError(msg: String?)
        fun getMovieComplete()
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

    fun requestMovie(apiKey: String, sortBy: String, page: Long): Observable<Response<MovieDao>> =
            movieApi.getMovie(apiKey, sortBy, page).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn { Response.success(null) }

    fun requestMovie(apiKey: String, sortBy: String, callback: MovieModelCallback): Disposable =
            requestMovie(apiKey, sortBy, getNextPage()).subscribe({ checkResult(it, callback) },
                    { requestMovieError(callback, MovieData.retrieveMovieFailure().str) })

    fun nextPageAvailable(): Boolean = nextPageAvailable

    private fun checkResult(response: Response<MovieDao>?, callback: MovieModelCallback) {
        val result = convertToMovieData(response)
        when (result) {
            is MovieData.Success -> {
                nextPageAvailable = page <= 10
                when {
                    nextPageAvailable -> callback.getMovieSuccess(result)
                    !nextPageAvailable -> callback.getMovieComplete()
                }
            }
            is MovieData.Failure -> requestMovieError(callback, result.str)
        }
    }

    private fun requestMovieError(callback: MovieModelCallback, message: String?) {
        nextPageAvailable = false
        callback.getMovieError(message)
    }

    private fun getNextPage(): Long {
        if (nextPageAvailable) return page++
        else return 1
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