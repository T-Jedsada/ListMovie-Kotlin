package com.ponthaitay.listmovie.kotlin.movie

sealed class MovieData {
    data class Success(var movieDao: MovieDao) : MovieData()
    data class View(var allResult: MutableList<MovieDao.ResultDetail>, var newResult: MutableList<MovieDao.ResultDetail>) : MovieData()
    data class Failure(val str: String) : MovieData()

    companion object {
        fun retrieveMovieSuccess(body: MovieDao?) = MovieData.Success(body!!)
        fun retrieveMovieFailure() = MovieData.Failure("Sorry, Something error!")
    }
}