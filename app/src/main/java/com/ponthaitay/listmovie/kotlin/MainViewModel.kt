package com.ponthaitay.listmovie.kotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel(), MovieModel.MovieModelCallback {

    var liveDataMovie: MutableLiveData<MovieData>? = null
    var movieModel = MovieModel.instance

    fun getMovie(apiKey: String, sortBy: String): LiveData<MovieData> {
        liveDataMovie = MutableLiveData()
        if (movieModel.nextPageAvailable()) movieModel.requestMovie(apiKey, sortBy, this)
        return liveDataMovie as MutableLiveData<MovieData>
    }

    override fun getMovieSuccess(data: MovieData?) {
        liveDataMovie?.value = data
    }

    override fun getMovieError(msg: String?) {
        
    }

    override fun getMovieComplete() {

    }
}