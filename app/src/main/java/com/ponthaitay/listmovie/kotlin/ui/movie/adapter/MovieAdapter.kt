package com.ponthaitay.listmovie.kotlin.ui.movie.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ponthaitay.listmovie.kotlin.service.model.MovieDao

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_MOVIE = 1
    private val TYPE_LOAD_MORE = 2
    private var listItem: MutableList<MovieDao.ResultDetail> = mutableListOf()
    private var movieCallback: MovieAdapterCallback? = null
    private var nextPageAvailable = false

    interface MovieAdapterCallback {
        fun loadMoreMovie()
    }

    fun setData(data: MutableList<MovieDao.ResultDetail>, nextPageAvailable: Boolean) {
        this.nextPageAvailable = nextPageAvailable
        listItem.addAll(data)
        notifyDataSetChanged()
    }

    fun addMovie(data: MutableList<MovieDao.ResultDetail>, nextPageAvailable: Boolean) {
        this.nextPageAvailable = nextPageAvailable
        listItem.addAll(listItem.size, data)
        notifyItemInserted(listItem.size)
    }

    fun removeViewLoadMore() {
        this.nextPageAvailable = false
        notifyItemInserted(listItem.size)
    }

    fun setMovieCallback(callback: MovieAdapterCallback) {
        movieCallback = callback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? = when (viewType) {
        TYPE_MOVIE -> MovieViewHolder(viewGroup!!)
        TYPE_LOAD_MORE -> LoadMoreViewHolder(viewGroup!!)
        else -> null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.setDisplayItem(listItem[position])
            is LoadMoreViewHolder -> if (nextPageAvailable) movieCallback?.loadMoreMovie()
        }
    }

    override fun getItemCount(): Int = when (nextPageAvailable) {
        true -> listItem.size + 1
        else -> listItem.size
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        listItem.size -> TYPE_LOAD_MORE
        else -> TYPE_MOVIE
    }
}