package com.ponthaitay.listmovie.kotlin.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ponthaitay.listmovie.kotlin.movie.MovieData.Companion.retrieveMovieFailure

class MainViewModel : ViewModel(), MovieModel.MovieModelCallback {

    private var liveDataMovie: MutableLiveData<MovieData>? = null
    private var movieDataSuccess = MovieData.View(mutableListOf(), mutableListOf())
    private var movieModel = MovieModel.instance

    fun getMovie(apiKey: String, sortBy: String, loadMore: Boolean): LiveData<MovieData> {
        when (liveDataMovie) {
            null -> {
                liveDataMovie = MutableLiveData()
                movieModel.requestMovie(apiKey, sortBy, this)
            }
            else -> {
                if (loadMore) movieModel.requestMovie(apiKey, sortBy, this)
            }
        }
        return liveDataMovie as MutableLiveData<MovieData>
    }

    fun getNextPageAvailable() = movieModel.nextPageAvailable()

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
}