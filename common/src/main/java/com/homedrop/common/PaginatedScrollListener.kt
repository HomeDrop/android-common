package com.homedrop.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Infinite scroll listener
 * @param layoutManager Layout manager of recyclerview
 */
abstract class PaginatedScrollListener (
    private val layoutManager: LinearLayoutManager,
    private val paginationSize: Int
): RecyclerView.OnScrollListener() {

    //Offset for pagination to trigger, try to keep it 20% of paged size for better visual
    private val offset = paginationSize * 0.2

    // Total number of items currently present in the data set
    private var totalItemCount = 0

    // True if loading is done at top
    private var topLoading = true

    // True if loading is done at bottom
    private var bottomLoading = true

    /**
     * Call this once you are done adding items at top position of the recyclerview
     */
    fun topLoadingDone() {
        topLoading = true
    }

    /**
     * Call this once you are done adding items at bottom position of the recyclerview
     */
    fun bottomLoadingDone() {
        bottomLoading = true
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy == 0) {
            return
        }

        onScroll()
        totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        /**
         * For pagination trigger
         */
        if (dy > 0) {
            /**
             * Scroll down
             */
            onScrollingToBottom(layoutManager.findLastCompletelyVisibleItemPosition())
            if (bottomLoading) {
                if (lastVisibleItemPosition > (totalItemCount - offset)) {
                    bottomLoading = false
                    onLoadMore(SCROLL_DOWN)
                }
            }
        } else {
            /**
             * Scroll up
             */
            if (lastVisibleItemPosition < totalItemCount - 1) {
                onScrollingToTop()
            }
            if (topLoading) {
                if (firstVisibleItemPosition < offset) {
                    topLoading = false
                    onLoadMore(SCROLL_UP)
                }
            }
        }

    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (!recyclerView.canScrollVertically(1)) {
            onBottomReached()
        }
    }

    open fun onLoadMore(@ScrollState scrollState: Int) {}

    open fun onBottomReached() {}

    open fun onScrollingToTop() {}

    open fun onScrollingToBottom(lastItemPosition: Int) {}

    open fun onScroll() {}

}