package com.homedrop.common.ktx

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.widget.Toast

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun Context?.showShortToast(text: String?) {
    if (this == null || text.isNullOrEmpty()) return
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity?.showShortToast(text: String?) {
    if (this == null || text.isNullOrEmpty()) return
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}