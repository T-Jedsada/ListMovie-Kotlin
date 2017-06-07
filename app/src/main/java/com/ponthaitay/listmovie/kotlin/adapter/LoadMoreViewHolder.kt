package com.ponthaitay.listmovie.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ponthaitay.listmovie.kotlin.R

class LoadMoreViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.progress_item, viewGroup, false))