package com.homedrop.common.util.viewpager2

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.homedrop.common.ktx.dpToPx

class HorizontalMarginItemDecoration(horizontalMargin: Int) : RecyclerView.ItemDecoration() {

    private val horizontalMarginInPx: Int = dpToPx(horizontalMargin)

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.right = horizontalMarginInPx
        outRect.left = horizontalMarginInPx
    }

}