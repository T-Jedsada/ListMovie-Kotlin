package com.ponthaitay.listmovie.kotlin

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpacesItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect?.left = space
        outRect?.right = space
        outRect?.bottom = space
        if (parent?.getChildLayoutPosition(view) == 0) outRect?.top = space
    }
}