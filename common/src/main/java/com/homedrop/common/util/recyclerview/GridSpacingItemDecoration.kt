package com.homedrop.common.util.recyclerview

import com.homedrop.common.ktx.dpToPx
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val edge: Edge,
    private val includeEdge: Boolean = true,
    private val isReverse: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        val column = position % spanCount

        if (includeEdge) {
            outRect.left = dpToPx(edge.left) - column * dpToPx(edge.left) / spanCount
            outRect.right = (column + 1) * dpToPx(edge.right) / spanCount

            if (position < spanCount) {
                if (isReverse)
                    outRect.bottom = dpToPx(edge.bottom)
                else
                    outRect.top = dpToPx(edge.top)
            }

            if (isReverse)
                outRect.top = dpToPx(edge.top)
            else
                outRect.bottom = dpToPx(edge.bottom)
        } else {
            outRect.left = column * dpToPx(edge.left) / spanCount
            outRect.right = dpToPx(edge.right) - (column + 1) * dpToPx(edge.right) / spanCount

            if (position >= spanCount) {
                if (isReverse)
                    outRect.bottom = dpToPx(edge.bottom)
                else
                    outRect.top = dpToPx(edge.top)
            }
        }
    }

}