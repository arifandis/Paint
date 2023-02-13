package com.example.paint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class DrawPath @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private lateinit var btmView: Bitmap
    private var paint: Paint? = null
    private var mPath: Path? = null
    private var pathList = mutableListOf<Bitmap>()
    private var undonePathList = mutableListOf<Bitmap>()
    private var mX: Float? = null
    private var mY: Float? = null
    private var touchTolerance: Float = 4f
    private var mCanvas: Canvas? = null
    private var size = 10f
    private var color = resources.getColor(R.color.purple_200)

    init {
        paint = Paint()
        mPath = Path()
        paint!!.color = color
        paint!!.strokeWidth = size
        paint!!.style = Paint.Style.STROKE
        paint!!.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        btmView = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(btmView)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(btmView, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val xPos: Float = event!!.x
        val yPos: Float = event.y
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(xPos, yPos)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(xPos, yPos)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                addAction(getBitmap())
            }
            else -> {

            }
        }
        return true
    }

    private fun touchUp() {
        mPath?.reset()
    }

    private fun touchMove(xPos: Float, yPos: Float) {
        val dx = abs(xPos - mX!!)
        val dy = abs(yPos - mY!!)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            mPath?.quadTo(mX!!, mY!!, (xPos + mX!!) / 2, (yPos + mY!!) / 2)

            mX = xPos
            mY = yPos

            mCanvas?.drawPath(mPath!!, paint!!)
            invalidate()
        }
    }

    private fun touchStart(xPos: Float, yPos: Float) {
        mPath!!.moveTo(xPos, yPos)
        mX = xPos
        mY = yPos
    }

    fun setUndo() {
        val size = pathList.size
        if (size > 0) {
            undonePathList.add(pathList[size - 1])
            pathList.removeAt(size - 1)
            if (pathList.size > 0) {
                btmView = pathList[pathList.size - 1]
            } else {
                btmView = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            }
            mCanvas = Canvas(btmView)
            invalidate()
        }
    }

    fun setRedo() {
        val size = undonePathList.size
        if (size > 0) {
            pathList.add(undonePathList[size - 1])
            undonePathList.removeAt(size - 1)
            if (pathList.size > 0) {
                btmView = pathList[pathList.size - 1]
            } else {
                btmView = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            }
            mCanvas = Canvas(btmView)
            invalidate()
        }
    }

    fun enableEraser() {
        mPath?.reset()
        paint?.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    fun disableEraser() {
        paint?.xfermode = null
        paint?.shader = null
        paint?.maskFilter = null
    }

    private fun getBitmap(): Bitmap {
        this.isDrawingCacheEnabled = true
        this.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(this.drawingCache)
        this.isDrawingCacheEnabled = false
        return bitmap
    }

    private fun addAction(bitmap: Bitmap) {
        pathList.add(bitmap)
    }

    fun setColor(customColor: Int) {
        color = customColor
        paint!!.color = customColor
    }

    fun setSize(customSize: Float) {
        size = customSize
        paint!!.strokeWidth = customSize
    }
}