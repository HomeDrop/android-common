package com.homedrop.common.ktx

import android.content.Context
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.homedrop.common.base.BaseViewType

/**
 * View ktx
 */

operator fun BaseViewType.plus(elements: Collection<BaseViewType>?): List<BaseViewType> {
    val result = ArrayList<BaseViewType>((elements?.size ?: 0) + 1)
    result.add(this)
    if (!elements.isNullOrEmpty()) {
        result.addAll(elements)
    }
    return result
}

fun showKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

operator fun Collection<BaseViewType>?.plus(element: BaseViewType): List<BaseViewType> {
    val result = ArrayList<BaseViewType>((this?.size ?: 0) + 1)
    if (!this.isNullOrEmpty()) {
        result.addAll(this)
    }
    result.add(element)
    return result
}

var View.shown
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.setRoundedCorners(cornerRadius: Int) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.width, view.height, dpToPx(cornerRadius).toFloat())
        }
    }
    clipToOutline = true
}

fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showShortToast(context: Context?, text: String?) {
    if (context == null || text.isNullOrEmpty()) return
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun showLongToast(context: Context, text: String?) {
    if (text.isNullOrEmpty()) return
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

fun showSomethingWentWrongToast(context: Context) {
    showShortToast(context, "Something went wrong!")
}

fun showShortSnack(view: View, text: String?) {
    if (text.isNullOrEmpty()) return
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}

fun showLongSnack(view: View, text: String?) {
    if (text.isNullOrEmpty()) return
    Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
}

fun showActionSnack(view: View, text: String?, action: String, listener: () -> Unit) {
    if (text.isNullOrEmpty()) return
    val snack = Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE)
    snack.setAction(action) {
        listener()
        snack.dismiss()
    }
    snack.show()
}