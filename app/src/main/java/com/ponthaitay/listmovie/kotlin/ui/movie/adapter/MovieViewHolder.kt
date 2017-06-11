package com.ponthaitay.listmovie.kotlin.ui.movie.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ponthaitay.listmovie.kotlin.R
import com.ponthaitay.listmovie.kotlin.service.model.MovieDao
import com.ponthaitay.listmovie.kotlin.ui.extentions.loadUrl
import com.ponthaitay.listmovie.kotlin.ui.extentions.title
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.movie_item, viewGroup, false)) {

    fun setDisplayItem(data: MovieDao.ResultDetail?) {
        itemView.tv_name_movie.title(data?.title)
        itemView.iv_movie.loadUrl(data?.backdropPath)
    }
}