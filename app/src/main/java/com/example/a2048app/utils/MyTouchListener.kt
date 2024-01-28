package com.example.a2048app.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import com.example.a2048app.data.SideEnum
import kotlin.math.abs

class MyTouchListener(private val context: Context) : View.OnTouchListener {
    private val gestureDetector = GestureDetector(context, MyGestureDetector())
    private var actionSideEnumListener: ((SideEnum) -> Unit)? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    private inner class MyGestureDetector : SimpleOnGestureListener() {
        override fun onFling(
            start: MotionEvent?,
            end: MotionEvent,
            velocityX: Float,
            velocityY: Float,
        ): Boolean {

            if (abs(start!!.x - end.x) <= 100 && abs(start.y - end.y) <= 100) return true

            if (abs(start.x - end.x) > abs(start.y - end.y)) {
                if (start.x > end.x) actionSideEnumListener?.invoke(SideEnum.LEFT)
                else actionSideEnumListener?.invoke(SideEnum.RIGHT)
            } else {
                if (start.y > end.y) actionSideEnumListener?.invoke(SideEnum.UP)
                else actionSideEnumListener?.invoke(SideEnum.DOWN)
            }

            return super.onFling(start, end, velocityX, velocityY)
        }
    }

    fun setActionSideEnumListener(block: ((SideEnum) -> Unit)) {
        actionSideEnumListener = block
    }
}