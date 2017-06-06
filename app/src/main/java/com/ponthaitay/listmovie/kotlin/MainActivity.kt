package com.ponthaitay.listmovie.kotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle

class MainActivity : LifecycleAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewModelProviders.of(this)
                .get(MainViewModel::class.java)
                .getMovie(getString(R.string.api_key), "popularity.desc")
                .observe(this, Observer { it.toString() })
    }
}