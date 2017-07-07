package com.ponthaitay.listmovie.kotlin.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.ponthaitay.listmovie.kotlin.R
import kotlinx.android.synthetic.main.activity_simple.*

infix fun TextView.tile(title: String?) = {
    this.text = title
}

class SimpleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
        tv_text tile ("20Scoops CNX")
    }
}


