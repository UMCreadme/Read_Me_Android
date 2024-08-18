package com.example.readme.ui.home.make.preview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import kotlin.math.max


class FlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    var horizontalSpacing = 0
    var verticalSpacing = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var width = 0
        var height = 0
        var lineHeight = 0
        var lineWidth = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec)
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight

                if (lineWidth + childWidth > widthSize) {
                    width = max(width, lineWidth)
                    lineWidth = childWidth
                    height += lineHeight + verticalSpacing
                    lineHeight = childHeight
                } else {
                    lineWidth += childWidth + horizontalSpacing
                    lineHeight = max(lineHeight, childHeight)
                }
            }
        }
        width = max(width, lineWidth)
        height += lineHeight

        setMeasuredDimension(
            if (widthMode == MeasureSpec.EXACTLY) widthSize else width,
            if (heightMode == MeasureSpec.EXACTLY) heightSize else height
        )
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        var xPos = 0
        var yPos = 0
        var lineHeight = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight
                if (xPos + childWidth > width) {
                    xPos = 0
                    yPos += lineHeight + verticalSpacing
                    lineHeight = 0
                }
                child.layout(xPos, yPos, xPos + childWidth, yPos + childHeight)
                xPos += childWidth + horizontalSpacing
                lineHeight = max(lineHeight, childHeight)
            }
        }
    }
}
