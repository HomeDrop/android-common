package com.homedrop.common.util.recyclerview

import com.homedrop.common.ktx.dpToPx
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinearHorizontalItemDecoration(val edge: Edge) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val layoutManager = parent.layoutManager as LinearLayoutManager

        if (layoutManager.orientation == RecyclerView.HORIZONTAL) {
            outRect.apply {
                top = dpToPx(edge.top)
                bottom = dpToPx(edge.bottom)
                left = if (position == 0) {
                    edge.left
                } else {
                    edge.left / 2
                }
                right = if (position == parent.childCount) {
                    edge.right
                } else {
                    edge.right / 2
                }
            }
        }
    }

}