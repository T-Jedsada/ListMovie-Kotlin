package com.ponthaitay.listmovie.kotlin.ui.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import com.ponthaitay.listmovie.kotlin.R
import com.ponthaitay.listmovie.kotlin.service.extentions.providesMovieAPIs
import com.ponthaitay.listmovie.kotlin.service.model.MovieData
import com.ponthaitay.listmovie.kotlin.ui.SpacesItemDecoration
import com.ponthaitay.listmovie.kotlin.ui.base.LifecycleAppCompatActivity
import com.ponthaitay.listmovie.kotlin.ui.extentions.snack
import com.ponthaitay.listmovie.kotlin.ui.movie.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LifecycleAppCompatActivity(), MovieAdapter.MovieAdapterCallback {

    private val SORT_BY = "popularity.desc"
    private lateinit var API_KEY: String
    private lateinit var adapterMovie: MovieAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupInstance()
        setupView()
        mainViewModel.getListMovie(API_KEY, SORT_BY)
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
                            container.snack(it.str, Snackbar.LENGTH_SHORT)
                            adapterMovie.removeViewLoadMore()
                        }
                    }
                })
    }

    private fun setupInstance() {
        API_KEY = getString(R.string.api_key)
        mainViewModel = ViewModelProviders.of(this, MainViewModel.Factory(providesMovieAPIs())).get(MainViewModel::class.java)
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
        mainViewModel.requestMovie(API_KEY, SORT_BY)
    }
}