package com.ponthaitay.listmovie.kotlin.ui.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ponthaitay.listmovie.kotlin.service.model.MovieData
import com.ponthaitay.listmovie.kotlin.service.model.MovieData.Companion.retrieveMovieFailure
import com.ponthaitay.listmovie.kotlin.service.repository.MovieApi
import com.ponthaitay.listmovie.kotlin.service.repository.MovieRepository

class MainViewModel(var movieApi: MovieApi) : ViewModel(), MovieRepository.MovieModelCallback {

    private var liveDataMovie: MutableLiveData<MovieData>? = null
    private var movieDataSuccess = MovieData.View(mutableListOf(), mutableListOf())
    private var movieRepository: MovieRepository? = null

    fun getListMovie(apiKey: String, sortBy: String): LiveData<MovieData> {
        when (liveDataMovie) {
            null -> {
                movieRepository = MovieRepository(movieApi)
                liveDataMovie = MutableLiveData()
                requestMovie(apiKey, sortBy)
            }
        }
        return liveDataMovie as MutableLiveData<MovieData>
    }

    fun requestMovie(apiKey: String, sortBy: String) {
        movieRepository?.requestMovie(apiKey, sortBy, this)
    }

    fun getNextPageAvailable() = movieRepository?.nextPageAvailable()

    override fun requestMovieSuccess(data: MovieData.Success) {
        movieDataSuccess.newResult = data.movieDao.result
        movieDataSuccess.allResult.addAll(movieDataSuccess.allResult.size, data.movieDao.result)
        liveDataMovie?.value = movieDataSuccess
    }

    override fun requestMovieError(msg: String?) {
        liveDataMovie?.value = retrieveMovieFailure(msg)
    }

    class Factory(var movieApi: MovieApi) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(movieApi) as T
    }
}