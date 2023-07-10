package com.homedrop.common.util.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homedrop.common.SCROLL_DOWN
import com.homedrop.common.SCROLL_UP

/**
 * Scroll listener for chatroom detail screen
 * @param mLinearLayoutManager Layout manager of chatroom recyclerview
 */
open class ScrollListener(
    private val mLinearLayoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    companion object {
        //Offset for pagination to trigger, try to keep it 20% of paged size for better visual
        private const val OFFSET = 50 * 0.2
    }

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

    fun reset() {
        totalItemCount = 0
        topLoading = true
        bottomLoading = true
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy == 0) {
            return
        }

        totalItemCount = mLinearLayoutManager.itemCount
        val firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition()

        /**
         * For pagination trigger
         */
        if (dy > 0) {
            /**
             * Scroll down
             */
            onScroll(SCROLL_DOWN)
            onScrollingToBottom(mLinearLayoutManager.findLastCompletelyVisibleItemPosition())
            if (bottomLoading) {
                if (lastVisibleItemPosition > (totalItemCount - OFFSET)) {
                    bottomLoading = false
                    onLoadMore(SCROLL_DOWN)
                }
            }
        } else {
            /**
             * Scroll up
             */
            onScroll(SCROLL_UP)
            if (lastVisibleItemPosition < totalItemCount - 1) {
                onScrollingToTop()
            }
            if (topLoading) {
                if (firstVisibleItemPosition < OFFSET) {
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

    open fun onLoadMore(scrollState: Int) {}

    open fun onBottomReached() {}

    open fun onScrollingToTop() {}

    open fun onScrollingToBottom(lastItemPosition: Int) {}

    open fun onScroll(scrollState: Int) {}
}