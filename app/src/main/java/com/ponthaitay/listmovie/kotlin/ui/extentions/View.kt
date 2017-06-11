package com.ponthaitay.listmovie.kotlin.ui.extentions

import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

fun TextView.title(title: String?) {
    text = title
}

fun ImageView.loadUrl(path: String?) = Picasso.with(this.context)
        .load("http://image.tmdb.org/t/p/w780/" + path)
        .placeholder(android.R.color.darker_gray)
        .error(android.R.color.darker_gray)
        .into(this)

fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.show()
}