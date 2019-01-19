package com.lubulwa.kulio.ui.component

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager



abstract class InfiniteScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = linearLayoutManager.childCount
            val totalItemCount = linearLayoutManager.itemCount
            val pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition()

            if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                loadMore()
            }
        }
    }

    protected abstract fun loadMore()

}