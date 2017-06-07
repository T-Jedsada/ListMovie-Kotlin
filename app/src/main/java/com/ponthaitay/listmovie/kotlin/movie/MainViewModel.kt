package com.ponthaitay.listmovie.kotlin.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ponthaitay.listmovie.kotlin.movie.MovieData.Companion.retrieveMovieFailure

class MainViewModel : ViewModel(), MovieModel.MovieModelCallback {

    private var liveDataMovie: MutableLiveData<MovieData>? = null
    private var movieModel = MovieModel.Companion.instance

    fun getMovie(apiKey: String, sortBy: String): LiveData<MovieData> {
        liveDataMovie = MutableLiveData()
        if (movieModel.nextPageAvailable()) movieModel.requestMovie(apiKey, sortBy, this)
        return liveDataMovie as MutableLiveData<MovieData>
    }

    override fun getMovieSuccess(data: MovieData?) {
        Log.d("Success", "OK")
        liveDataMovie?.value = data
    }

    override fun getMovieError(msg: String?) {
        Log.d("POND Error", msg)
        liveDataMovie?.value = retrieveMovieFailure()
    }

    override fun getMovieComplete() {
        Log.d("Error", "Error")
        liveDataMovie?.value = retrieveMovieFailure()
    }
}