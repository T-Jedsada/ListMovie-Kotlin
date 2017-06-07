package com.ponthaitay.listmovie.kotlin.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.ponthaitay.listmovie.kotlin.LifecycleAppCompatActivity
import com.ponthaitay.listmovie.kotlin.R
import com.ponthaitay.listmovie.kotlin.adapter.MoviceAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LifecycleAppCompatActivity() {

    private lateinit var adapterMovie: MoviceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupInstance()
        setupView()

        ViewModelProviders.of(this)
                .get(MainViewModel::class.java)
                .getMovie(getString(R.string.api_key), "popularity.desc")
                .observe(this, Observer { Log.d("POND", "test $it") })
    }

    private fun setupInstance() {
        adapterMovie = MoviceAdapter()
    }

    private fun setupView() {
        list_movie.layoutManager = LinearLayoutManager(this)
        list_movie.setHasFixedSize(true)
        list_movie.adapter = adapterMovie
    }
}