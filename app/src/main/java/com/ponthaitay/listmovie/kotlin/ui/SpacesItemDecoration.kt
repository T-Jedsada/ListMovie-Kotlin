package com.ponthaitay.listmovie.kotlin.ui

class SpacesItemDecoration(val space: Int) : android.support.v7.widget.RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: android.graphics.Rect?, view: android.view.View?, parent: android.support.v7.widget.RecyclerView?, state: android.support.v7.widget.RecyclerView.State?) {
        outRect?.left = space
        outRect?.right = space
        outRect?.bottom = space
        if (parent?.getChildLayoutPosition(view) == 0) outRect?.top = space
    }
}