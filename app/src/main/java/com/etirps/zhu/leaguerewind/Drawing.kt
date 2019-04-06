package com.etirps.zhu.leaguerewind

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.MotionEvent

data class LineStroke(var paint: Paint, var path: Path)
data class TokenPlace(var paint: Paint, var matrix: Matrix, var token: Bitmap)

class Drawing(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var myPaint = Paint()
    private var currentX = 0f
    private var currentY = 0f
    private var startX = 0f
    private var startY = 0f
    private var myLines = mutableListOf<LineStroke>()
    private var myTokens = mutableListOf<TokenPlace>()

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

        if((context.applicationContext as ApplicationData).clearFlag) {
            myLines.clear()
            myTokens.clear()
            (context.applicationContext as ApplicationData).clearFlag = false
        }

        for (line in myLines) {
            canvas.drawPath(line.path, line.paint)
        }

        for(token in myTokens) {
            canvas.drawBitmap(token.token, token.matrix, token.paint)
        }
    }

    private fun pressDown(x: Float, y: Float) {
        val appData = context.applicationContext as ApplicationData

        when(appData.drawMode) {
            DrawingModes.PEN -> drawLine(x, y, appData.colorInt, appData.strokeWidth.toFloat())
            DrawingModes.TOKEN -> {
                drawToken(x, y, appData.tokenIndex, appData.isEnemy, appData.isWard)
            }
        }
    }

    private fun drawLine(x: Float, y: Float, lineColor: Int, stroke: Float) {
        val newPaint = Paint()

        newPaint.apply {
            color = lineColor
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeWidth = stroke
            isAntiAlias = true
        }

        val newPath = Path()

        val newLine = LineStroke(newPaint, newPath)

        newLine.path.moveTo(x, y)
        currentX = x
        currentY = y

        myLines.add(newLine)
    }

    private fun drawToken(x: Float, y: Float, tokenIndex: Int, isEnemy: Boolean, isWard: Boolean) {
        val matrix = Matrix()


        if(isWard) {
            matrix.setScale(0.15f, 0.15f)
        } else {
            matrix.setScale(0.25f, 0.25f)
        }
        matrix.postTranslate(x - 30, y - 30)

        val tokenManager = (context.applicationContext as ApplicationData).tokenImageManager
        var tokenName: String

        tokenName = if(isWard) {
            tokenManager.wardTitles[tokenIndex]
        } else {
            tokenManager.championTitles[tokenIndex]
        }

        val bmp = BitmapFactory.decodeResource(resources, context.resIdByName(tokenName.clean(), "drawable"))


        val bitmapBorder = Bitmap.createBitmap(bmp.width, bmp.height, bmp.config)

        if(isEnemy) {
            val cv = Canvas(bitmapBorder)
            cv.drawColor(Color.RED)
            cv.drawBitmap(bmp, 10f, 10f, null)
        }

        val paint = Paint()
        paint.apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeWidth = 10f
            isAntiAlias = true
        }

        if(isEnemy) {
            myTokens.add(TokenPlace(paint, matrix, bitmapBorder))
        } else {
            myTokens.add(TokenPlace(paint, matrix, bmp))
        }

    }

    private fun pressUp() {
        if(!myLines.isEmpty()) {
            myLines.last().path.lineTo(currentX, currentY)
        }

        if((context.applicationContext as ApplicationData).drawMode == DrawingModes.TOKEN) {
            (context.applicationContext as ApplicationData).drawMode = DrawingModes.PEN
        }
    }

    private fun move(x : Float, y : Float) {
        if((context.applicationContext as ApplicationData).drawMode == DrawingModes.TOKEN) {
            return
        }

        if(!myLines.isEmpty()) {
            myLines.last().path.quadTo(currentX, currentY, (x + currentX)/2, (y + currentY)/2)
        }
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