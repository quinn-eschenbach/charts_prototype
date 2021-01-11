package com.example.charts.vitaldaten.bloodsugar

import android.content.res.Resources
import android.graphics.*
import android.graphics.Paint.Align
import android.graphics.drawable.Drawable
import android.util.TypedValue


class TextDrawable(res: Resources, private val mText: CharSequence, rectColor: Int) :
    Drawable() {
    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mIntrinsicWidth: Int
    private val mIntrinsicHeight: Int
    override fun draw(canvas: Canvas) {
        rectPaint.style = Paint.Style.FILL
        val bounds = bounds
        canvas.drawRect(
            RectF(bounds.left - 10f,bounds.top - 10f,bounds.right + 10f,bounds.bottom.toFloat()), rectPaint
        )
        canvas.drawText(
            mText, 0, mText.length,
            bounds.centerX().toFloat(), bounds.centerY().toFloat(), textPaint
        )
    }

    override fun getOpacity(): Int {
        return textPaint.alpha
    }

    override fun getIntrinsicWidth(): Int {
        return mIntrinsicWidth
    }

    override fun getIntrinsicHeight(): Int {
        return mIntrinsicHeight
    }

    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
    }

    override fun setColorFilter(filter: ColorFilter?) {
        textPaint.colorFilter = filter
    }

    companion object {
        private const val DEFAULT_COLOR = Color.BLACK
        private const val DEFAULT_TEXTSIZE = 15
    }

    init {
        textPaint.color =
            DEFAULT_COLOR
        rectPaint.color = rectColor
        textPaint.textAlign = Align.CENTER
        val textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            DEFAULT_TEXTSIZE.toFloat(), res.displayMetrics
        )
        textPaint.textSize = textSize
        mIntrinsicWidth = (textPaint.measureText(mText, 0, mText.length) + .5).toInt()
        mIntrinsicHeight = textPaint.getFontMetricsInt(null)
    }
}