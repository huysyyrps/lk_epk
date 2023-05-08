package com.example.lk_epk.view

import android.R.attr
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Region
import android.util.AttributeSet
import android.view.MotionEvent
import com.example.lk_epk.R
import com.example.lk_epk.util.LogUtil
import com.github.mikephil.charting.charts.LineChart
import kotlin.properties.Delegates


class BaseLineChart : LineChart {
    var paint = Paint()
    var gatePaint = Paint()
    var makerX1 by Delegates.notNull<Float>()
    var makerY1 by Delegates.notNull<Float>()
    var fragmentTag by Delegates.notNull<String>()
    private var gateX1 = 0f
    private var gateY1 = 0f
    private var downX = 0f
    private var downY = 0f
    private var canvasWidth = 0
    private var canvasHeight = 0
    private var moveTag = false
    private var defaultLeftLength = 35
    private var defaultRightLength = 55

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
        paint.color = context.getColor(R.color.red)
        paint.isAntiAlias = true
        paint.strokeWidth = 3.0f

        gatePaint.color = context.getColor(R.color.green)
        gatePaint.isAntiAlias = true
        gatePaint.strokeWidth = 12.0f
    }
    fun getSize(width: Float, height: Float) {
        makerX1 = width
        makerY1 = height
    }
    fun getTag(tag: String) {
        fragmentTag = tag
    }
    fun getGate(oneGateX1: Float, oneGateY1: Float) {
        gateX1 = oneGateX1
        gateY1 = oneGateY1
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvasWidth = canvas?.width!!
        canvasHeight = canvas?.height!!
        if (fragmentTag=="ScanAFragment"){
            //高度-20是因为X轴标签高度为20
            //十字横线
            canvas!!.drawLine(
                makerX1-15,
                makerY1-20,
                makerX1+15,
                makerY1-20,
                paint
            )
            //十字竖线
            canvas!!.drawLine(
                makerX1,
                makerY1-35,
                makerX1,
                makerY1-5,
                paint
            )
            //画闸门35+55为闸门默认长度
            if (gateX1>0&&gateY1>0){
                canvas!!.drawLine(
                    gateX1-defaultLeftLength,
                    gateY1-20,
                    gateX1+defaultRightLength,
                    gateY1-20,
                    gatePaint
                )
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                downX = event?.x
                downY = event?.y
                if (gateX1>0&&gateY1>0){
                    val region = Region(
                        (gateX1-defaultLeftLength).toInt(),
                        (gateY1-40).toInt(),
                        (gateX1+defaultRightLength).toInt(),
                        (gateY1+20).toInt()
                    )
                    if (region.contains(downX!!.toInt(),downY!!.toInt())){
                        LogUtil.e("TAG","${downX}--${downY}")
                        moveTag = false
                        gatePaint.color = context.getColor(R.color.red)
                    }else{
                        moveTag = true
                    }
                }
//                parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_MOVE->{
                if (moveTag){
                    val x = event?.x
                    val y = event?.y
                    LogUtil.e("TAG","${x}-move-${y}")
                    gateX1 = x
                    gateY1 = y
                } else {
                    var moveX = event?.x
                    var moveXLengh = moveX - downX
                    if (moveXLengh>0&&gateX1+moveXLengh<canvasWidth){
                        defaultRightLength = moveXLengh.toInt()
                    }else  if (moveXLengh<0&&gateX1-moveXLengh>80){
                        defaultRightLength = moveXLengh.toInt()
                    }
                }
                invalidate()
//                parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_UP->{
                moveTag = false
                gatePaint.color = context.getColor(R.color.green)
            }
        }
        return true
//        return super.onTouchEvent(event)

    }
}