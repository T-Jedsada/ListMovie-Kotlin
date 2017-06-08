package com.ponthaitay.listmovie.kotlin.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ponthaitay.listmovie.kotlin.movie.MovieData.Companion.retrieveMovieFailure

class MainViewModel : ViewModel(), MovieModel.MovieModelCallback {

    private var liveDataMovie: MutableLiveData<MovieData>? = null
    private var movieDataSuccess = MovieData.View(mutableListOf(), mutableListOf())
    private var movieModel : MovieModel? = MovieModel.instance

    fun requestMovie(apiKey: String, sortBy: String) = movieModel?.requestMovie(apiKey, sortBy, this)

    fun getListMovie(apiKey: String, sortBy: String): LiveData<MovieData> {
        when (liveDataMovie) {
            null -> {
                liveDataMovie = MutableLiveData()
                requestMovie(apiKey, sortBy)
            }
        }
        return liveDataMovie as MutableLiveData<MovieData>
    }

    fun getNextPageAvailable() = movieModel?.nextPageAvailable()

    override fun requestMovieSuccess(data: MovieData?) {
        when (data) {
            is MovieData.Success -> {
                movieDataSuccess.newResult = data.movieDao.result
                movieDataSuccess.allResult.addAll(movieDataSuccess.allResult.size, data.movieDao.result)
                liveDataMovie?.value = movieDataSuccess
            }
        }
    }

    override fun requestMovieError(msg: String?) {
        liveDataMovie?.value = retrieveMovieFailure()
    }

    override fun onCleared() {
        super.onCleared()
        movieModel = null
    }
}