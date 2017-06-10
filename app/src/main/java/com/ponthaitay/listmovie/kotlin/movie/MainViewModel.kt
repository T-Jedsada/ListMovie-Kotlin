package com.ponthaitay.listmovie.kotlin.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ponthaitay.listmovie.kotlin.movie.MovieData.Companion.retrieveMovieFailure

class MainViewModel : ViewModel(), MovieModel.MovieModelCallback {

    private var liveDataMovie: MutableLiveData<MovieData>? = null
    private var movieDataSuccess = MovieData.View(mutableListOf(), mutableListOf())
    private var movieModel: MovieModel? = null

    fun setMovieModel(movieModel: MovieModel) {
        this.movieModel = movieModel
    }

    fun getListMovie(apiKey: String, sortBy: String): LiveData<MovieData> {
        when (liveDataMovie) {
            null -> {
                movieModel = MovieModel()
                liveDataMovie = MutableLiveData()
                requestMovie(apiKey, sortBy)
            }
        }
        return liveDataMovie as MutableLiveData<MovieData>
    }

    fun requestMovie(apiKey: String, sortBy: String) {
        movieModel?.requestMovie(apiKey, sortBy, this)
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

    class Factory : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel() as T
    }
}