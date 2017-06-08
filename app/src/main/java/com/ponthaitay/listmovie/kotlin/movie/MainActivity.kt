package com.ponthaitay.listmovie.kotlin.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import com.ponthaitay.listmovie.kotlin.LifecycleAppCompatActivity
import com.ponthaitay.listmovie.kotlin.R
import com.ponthaitay.listmovie.kotlin.SpacesItemDecoration
import com.ponthaitay.listmovie.kotlin.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LifecycleAppCompatActivity(), MovieAdapter.MovieAdapterCallback {

    private lateinit var adapterMovie: MovieAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupInstance()
        setupView()
        mainViewModel.getListMovie(getString(R.string.api_key), "popularity.desc")
                .observe(this, Observer {
                    when (it) {
                        is MovieData.View -> when (pb_loading.visibility) {
                            VISIBLE -> {
                                pb_loading.visibility = GONE
                                adapterMovie.setData(it.allResult, mainViewModel.getNextPageAvailable()!!)
                            }
                            GONE -> adapterMovie.addMovie(it.newResult, mainViewModel.getNextPageAvailable()!!)
                        }
                        is MovieData.Failure -> {
                            Log.d("POND", "error")
                        }
                    }
                })
    }

    private fun setupInstance() {
        mainViewModel = ViewModelProviders.of(this, MainViewModel.Factory()).get(MainViewModel::class.java)
        adapterMovie = MovieAdapter()
        adapterMovie.setMovieCallback(this)
    }

    private fun setupView() {
        list_movie.layoutManager = LinearLayoutManager(this)
        list_movie.setHasFixedSize(true)
        list_movie.addItemDecoration(SpacesItemDecoration(resources.getDimension(R.dimen.padding_item_decoration_default).toInt()))
        list_movie.adapter = adapterMovie
    }

    override fun loadMoreMovie() {
        mainViewModel.requestMovie(getString(R.string.api_key), "popularity.desc")
    }
}