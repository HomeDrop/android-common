package com.homedrop.common.util.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homedrop.common.ktx.dpToPx

class LinearItemDecoration(spacing: Int) : RecyclerView.ItemDecoration() {

    private val spacingInDp = dpToPx(spacing)

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
                top = spacingInDp
                bottom = spacingInDp
                left = if (position == 0) {
                    spacingInDp
                } else {
                    spacingInDp / 2
                }
                right = if (position == parent.childCount) {
                    spacingInDp
                } else {
                    spacingInDp
                }
            }
        } else {
            outRect.apply {
                left = spacingInDp
                right = spacingInDp
                top = if (position == 0) {
                    spacingInDp
                } else {
                    spacingInDp / 2
                }
                bottom = if (position == parent.childCount) {
                    spacingInDp
                } else {
                    spacingInDp
                }
            }
        }
    }

}