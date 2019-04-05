package com.etirps.zhu.leaguerewind

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent

data class LineStroke(var paint: Paint, var path: Path)

class Drawing(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var myPaint = Paint()
    private var myPath = Path()
    private var currentX = 0f
    private var currentY = 0f
    private var startX = 0f
    private var startY = 0f
    private var myLines = mutableListOf<LineStroke>()

    init {
        myPaint.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeWidth = 8f
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(myPath, myPaint)
    }

    private fun pressDown(x: Float, y: Float) {
        val appData = context.applicationContext as ApplicationData
        myPaint.color = appData.colorInt
        myPaint.strokeWidth = appData.strokeWidth.toFloat()

        myPath.moveTo(x, y)
        currentX = x
        currentY = y
    }

    private fun pressUp() {
        myPath.lineTo(currentX, currentY)
    }

    private fun move(x : Float, y : Float) {
        myPath.quadTo(currentX, currentY, (x + currentX)/2, (y + currentY)/2)
        currentX = x
        currentY = y
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = x
                startY = y
                pressDown(x, y)
            }
            MotionEvent.ACTION_MOVE -> move(x, y)
            MotionEvent.ACTION_UP -> pressUp()
        }
        invalidate()
        return true
    }

}