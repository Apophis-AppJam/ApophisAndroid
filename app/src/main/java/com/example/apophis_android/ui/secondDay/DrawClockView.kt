package com.example.apophis_android.ui.secondDay

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View

/**
 * Created By kimdahyee
 * on 01월 04일, 2020
 */
 
class DrawClockView(context: Context): View(context) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val startAngle = 0F
        val sweepAngle = 90F
        var paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 30F
        paint.color = Color.parseColor("#AB70F5")

        var oval = RectF(100F, 200F, 300F, 400F)
        canvas?.drawArc(oval, startAngle, sweepAngle, true, paint)
    }
}