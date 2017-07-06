package com.ponthaitay.listmovie.kotlin.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ponthaitay.listmovie.kotlin.R
import kotlinx.android.synthetic.main.activity_simple.*

class SimpleActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
        tv_text.text = "20Scoops CNX"
    }
}



