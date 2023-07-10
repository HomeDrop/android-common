package com.homedrop.common.util

import android.content.Context
import android.content.res.Resources

object DeviceUtil {

    /**
     * @return Pair(width, height) in pixels
     */
    fun getDeviceSize(): Pair<Int, Int> {
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels
        return Pair(width, height)
    }

    /**
     * @return width in pixels
     */
    fun getDeviceWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    /**
     * @return height in pixels
     */
    fun getDeviceHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun getStatusBarHeight(context: Context): Int {
        var height = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = context.resources.getDimensionPixelSize(resourceId)
        }
        return height
    }

}