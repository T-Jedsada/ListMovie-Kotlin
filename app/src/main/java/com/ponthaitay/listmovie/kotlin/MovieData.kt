package com.ponthaitay.listmovie.kotlin

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import java.math.BigDecimal

sealed class MovieData
data class Success(@SerializedName("page") val page: Int,
                   @SerializedName("total_result") val totalResult: Long,
                   @SerializedName("total_pages") val totalPages: Long,
                   @SerializedName("results") val result: ResultDetail) : MovieData()

data class Failure(val str: String) : MovieData()

data class ResultDetail(@SerializedName("vote_count") val voteCount: Int,
                        @SerializedName("id") val id: Long,
                        @SerializedName("video") val video: Boolean,
                        @SerializedName("vote_average") val voteAverage: BigDecimal,
                        @SerializedName("title") val title: String,
                        @SerializedName("popularity") val popularity: BigDecimal,
                        @SerializedName("poster_path") val posterPath: String,
                        @SerializedName("original_language") val originalLanguage: String,
                        @SerializedName("genre_ids") val genreIds: List<String>,
                        @SerializedName("backdrop_path") val backdropPath: String,
                        @SerializedName("adult") val adult: Boolean,
                        @SerializedName("overview") val overview: String,
                        @SerializedName("release_date") val releaseDate: String)

fun retrieveMovieFailure(): Response<MovieData>? {
    return Response.success(Failure("Sorry, api error."))
}