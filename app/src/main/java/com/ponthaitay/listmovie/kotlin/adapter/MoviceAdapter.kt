package com.ponthaitay.listmovie.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ponthaitay.listmovie.kotlin.movie.MovieData

class MoviceAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listItem: List<MovieData> = emptyList()
    var lastData: MovieData? = MovieData.retrieveMovieFailure()

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder?, p1: Int) {
        when (p0) {
            is MovieViewHolder -> {
            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): RecyclerView.ViewHolder? = when (p1) {
        1 -> MovieViewHolder(p0!!)
        2 -> LoadmoreViewHolder(p0!!)
        else -> null
    }

    override fun getItemCount(): Int = when (lastData) {
        is MovieData.Success -> listItem.size + 1
        else -> listItem.size
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        (listItem.size) -> 1
        else -> 2
    }
}