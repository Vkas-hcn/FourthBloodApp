package com.blue.colored.frog.flowing.light.thirdbloodpressure.wight
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

import android.graphics.RectF
import android.util.TypedValue
import androidx.core.content.res.ResourcesCompat
import com.blue.colored.frog.flowing.light.thirdbloodpressure.R

class VerticalPickerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var values: List<Int> = (0..250).toList()
    private var selectedIndex: Int = 0
    private var lastY: Float = 0f

    private var minValue: Int = 0
    private var maxValue: Int = 250
    private var onValueChangeListener: OnValueChangeListener? = null

    private val textPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20f, resources.displayMetrics)
        typeface = ResourcesCompat.getFont(context, R.font.poppins_black)

    }

    private val middleTextColor = Color.parseColor("#FF0894FF")
    private val sideTextColor = Color.parseColor("#CEEAFF")
    private val textSize = 60f

    private val gap = 16 // gap between numbers in dp
    private val gapPx = gap * context.resources.displayMetrics.density

    private val backgroundPaint = Paint().apply {
        color = Color.parseColor("#E6F4FF")
        isAntiAlias = true
    }
    private val backgroundWidth = 91 * context.resources.displayMetrics.density
    private val backgroundHeight = 40 * context.resources.displayMetrics.density

    init {
        textPaint.textSize = textSize
    }
    interface OnValueChangeListener {
        fun onValueChange(newValue: Int)
    }
    fun setOnValueChangeListener(listener: OnValueChangeListener) {
        onValueChangeListener = listener
    }
    fun setRange(min: Int, max: Int) {
        minValue = max(0, min)
        maxValue = min(250, max)
        updateValues()
        invalidate()
    }

    fun setDefaultValue(defaultValue: Int) {
        selectedIndex = max(0, min(values.size - 1, values.indexOf(defaultValue)))
        invalidate()
    }

    private fun updateValues() {
        values = (minValue..maxValue).toList()
    }

    fun getSelectedValue(): Int {
        return values.getOrNull(selectedIndex) ?: minValue
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (values.isEmpty()) return

        val width = width
        val height = height
        val centerY = height / 2

        // Draw middle text background
        val backgroundRect = RectF(
            (width - backgroundWidth) / 2,
            centerY - backgroundHeight / 2,
            (width + backgroundWidth) / 2,
            centerY + backgroundHeight / 2
        )
        canvas.drawRoundRect(backgroundRect, 10f, 10f, backgroundPaint)

        // Draw middle text
        textPaint.color = middleTextColor
        drawCenteredText(canvas, values[selectedIndex].toString(), centerY.toFloat())

        // Draw upper text
        if (selectedIndex > 0) {
            textPaint.color = sideTextColor
            drawCenteredText(canvas, values[selectedIndex - 1].toString(), centerY - (textSize + gapPx))
        }

        // Draw lower text
        if (selectedIndex < values.size - 1) {
            textPaint.color = sideTextColor
            drawCenteredText(canvas, values[selectedIndex + 1].toString(), centerY + (textSize + gapPx))
        }
    }

    private fun drawCenteredText(canvas: Canvas, text: String, y: Float) {
        val x = (width / 2).toFloat()
        val yPos = y - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(text, x, yPos, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaY = event.y - lastY
                if (abs(deltaY) > textSize / 2) {
                    if (deltaY < 0 && selectedIndex < values.size - 1) {
                        setSelectedIndex(selectedIndex + 1)
                    } else if (deltaY > 0 && selectedIndex > 0) {
                        setSelectedIndex(selectedIndex - 1)
                    }
                    lastY = event.y
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun setSelectedIndex(index: Int) {
        if (selectedIndex != index) {
            selectedIndex = max(0, min(values.size - 1, index))
            onValueChangeListener?.onValueChange(values[selectedIndex])
            invalidate()
        }
    }
}


