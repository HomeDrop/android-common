package com.homedrop.common.util.image

import android.graphics.*
import android.graphics.drawable.Drawable

class ColorCircleDrawable(color: Int): Drawable() {

    private var mRadius = 0F
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        setColor(color)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(bounds.centerX().toFloat(), bounds.centerY().toFloat(), mRadius, mPaint)
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        mRadius = (bounds.width().coerceAtMost(bounds.height()) / 2).toFloat()
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("PixelFormat.TRANSLUCENT", "android.graphics.PixelFormat")
    )
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

}