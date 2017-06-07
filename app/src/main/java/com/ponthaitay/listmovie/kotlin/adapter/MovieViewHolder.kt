package com.ponthaitay.listmovie.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.ponthaitay.listmovie.kotlin.R
import com.ponthaitay.listmovie.kotlin.movie.MovieDao
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.movie_item, viewGroup, false)) {

    fun setDisplayItem(data: MovieDao.ResultDetail?) {
        itemView.tv_name_movie.text = data?.title
        itemView.iv_movie.loadUrl(data?.backdropPath)
    }

    fun ImageView.loadUrl(path: String?) {
        Picasso.with(itemView.context)
                .load("http://image.tmdb.org/t/p/w780/" + path)
                .placeholder(android.R.color.darker_gray)
                .error(android.R.color.darker_gray)
                .into(this)
    }
}