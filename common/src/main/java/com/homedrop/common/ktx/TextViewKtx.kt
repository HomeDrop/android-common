package com.homedrop.common.ktx

import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat

fun TextView.htmlText(text: String) {
    setText(HtmlCompat.fromHtml(text, 0))
}

fun TextView.htmlText(text: String, cb: (String) -> Unit) {
    val sequence = HtmlCompat.fromHtml(text, 0)
    val strBuilder = SpannableStringBuilder(sequence)
    val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
    for (span in urls) {
        makeLinkClickable(strBuilder, span, cb)
    }
    setText(strBuilder)
    movementMethod = LinkMovementMethod.getInstance()
}

private inline fun makeLinkClickable(
    strBuilder: SpannableStringBuilder,
    span: URLSpan,
    crossinline cb: (String) -> Unit
) {
    val start = strBuilder.getSpanStart(span)
    val end = strBuilder.getSpanEnd(span)
    val flags = strBuilder.getSpanFlags(span)
    val clickable: ClickableSpan = object : ClickableSpan() {
        override fun onClick(view: View) {
            cb(span.url)
        }
    }
    strBuilder.setSpan(clickable, start, end, flags)
    strBuilder.removeSpan(span)
}