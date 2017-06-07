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
        getMovie(false)
    }

    override fun loadMoreMovie() {
        getMovie(true)
    }

    private fun setupInstance() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        adapterMovie = MovieAdapter()
        adapterMovie.setMovieCallback(this)
    }

    private fun setupView() {
        list_movie.layoutManager = LinearLayoutManager(this)
        list_movie.setHasFixedSize(true)
        list_movie.addItemDecoration(SpacesItemDecoration(resources.getDimension(R.dimen.padding_item_decoration_default).toInt()))
        list_movie.adapter = adapterMovie
    }

    private fun getMovie(loadMore: Boolean) {
        mainViewModel.getMovie(getString(R.string.api_key), "popularity.desc", loadMore)
                .observe(this, Observer {
                    when (it)  {
                        is MovieData.View -> setResultMovie(it, loadMore)
                    }
                })
    }

    private fun setResultMovie(data: MovieData.View, loadMore: Boolean) {
        if (pb_loading.visibility == VISIBLE) pb_loading.visibility = GONE
        Log.d("POND", "$loadMore")
        when (loadMore) {
            true -> Log.d("POND", "Hi go grill")
            false -> adapterMovie.setData(data.newResult, mainViewModel.getNextPageAvailable())
        }
    }
}