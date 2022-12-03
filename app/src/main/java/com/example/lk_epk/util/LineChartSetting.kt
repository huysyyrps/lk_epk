package com.example.lk_epk.util

import android.graphics.Color
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet

object LineChartSetting {
    fun SettingLineChart(linechar:LineChart){
        linechar.setPinchZoom(false)//双指缩放
        linechar.setScaleEnabled(true) // 两个轴上的缩放,X,Y分别默认为true
        linechar.isScaleXEnabled = true // X轴上的缩放,默认true
        linechar.isScaleYEnabled = true // Y轴上的缩放,默认true
        linechar.setPinchZoom(false) // X,Y轴同时缩放，false则X,Y轴单独缩放,默认false
        linechar.description = null//设置显示在图表右下角的描述文字
        linechar.setDrawBorders(true)//启用/禁用绘制图表边框（图表周围的线条）
        linechar.setBorderColor(MyApplication.context.getColor(R.color.theme_color))
        //setVisibleXRangeMaximum(float maxXRange):设置x轴最多显示数据条数，（要在设置数据源后调用，否则是无效的）

        //X轴
        val xAxis = linechar.xAxis
        xAxis.textColor = MyApplication.context.resources.getColor(R.color.theme_color)
        xAxis.gridColor = MyApplication.context.resources.getColor(R.color.line)
        xAxis.axisLineColor = MyApplication.context.resources.getColor(R.color.theme_color)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setAvoidFirstLastClipping(true); //图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘

        //左侧Y轴
        val yLeftAxis = linechar.axisLeft
        yLeftAxis.textColor = MyApplication.context.resources.getColor(R.color.theme_color)
        yLeftAxis.gridColor = MyApplication.context.resources.getColor(R.color.line)//网格颜色
        yLeftAxis.axisLineColor = MyApplication.context.resources.getColor(R.color.theme_color)//轴线颜色
        yLeftAxis.spaceBottom = 10f // 最小值距离底部比例。默认10，y轴独有
        yLeftAxis.spaceTop = 10f // 设置最大值到图标顶部的距离占所有数据范围的比例。默认10，y轴独有
        yLeftAxis.axisMaximum = 100f//为此轴设置自定义最大值。
        yLeftAxis.setDrawGridLines(false)
//        yLeftAxis.axisMinimum = -30f


        //右侧Y轴
        val yRightAxis = linechar.axisRight
        yRightAxis.axisLineColor = MyApplication.context.resources.getColor(R.color.theme_color)//轴线颜色
        yRightAxis.setDrawLabels(false)//右侧轴线不显示标签
    }
}