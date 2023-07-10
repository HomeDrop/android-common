package com.homedrop.common.util.viewpager2

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.homedrop.common.ktx.dpToPx
import kotlin.math.abs

/**
 * https://stackoverflow.com/a/58088398
 */
class NearbyVisiblePageTransformer(
    private val nextItemVisibleMargin: Int,
    private val currentItemHorizontalMargin: Int
): ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val pageTranslationX = dpToPx(nextItemVisibleMargin) + dpToPx(currentItemHorizontalMargin)
        page.translationX = -pageTranslationX * position
        // Next line scales the item's height. You can remove it if you don't want this effect
        page.scaleY = 1 - (0.15f * abs(position))
        // If you want a fading effect uncomment the next line:
        // page.alpha = 0.25f + (1 - abs(position))
    }

}