package com.homedrop.common.ktx

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

fun fetchColor(context: Context, @ColorRes id: Int): Int {
    return ResourcesCompat.getColor(context.resources, id, null)
}

fun fetchDrawable(context: Context, @DrawableRes id: Int): Drawable {
    return ResourcesCompat.getDrawable(context.resources, id, null)!!
}