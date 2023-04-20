package com.example.lk_epk.view
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewTreeObserver
import com.github.mikephil.charting.charts.LineChart
import kotlin.properties.Delegates


class BaseLineChart : LineChart {
    var paint = Paint()
    var viewWidth by Delegates.notNull<Int>()
    var viewHeight by Delegates.notNull<Int>()

    constructor(context: Context) : super(context) {
        initView(context, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)

    }
    private fun initView(context: Context, attrs: AttributeSet?) {
        paint.color = -16776961
        paint.isAntiAlias = true
        paint.strokeWidth = 3.0f
    }
    public fun getSize(width: Int, height: Int) {
        viewWidth = width
        viewHeight = height
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}