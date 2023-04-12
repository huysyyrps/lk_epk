package com.example.lk_epk.fragment

import android.view.View
import com.example.lk_epk.R
import com.example.lk_epk.util.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_scan_a.*
import java.util.*


class ScanBFragment : BaseFragment(), View.OnClickListener {
    private lateinit var dataList: List<String>
    private var landList: ArrayList<Entry> = ArrayList()
    private lateinit var lineDataSet: LineDataSet
    private var i:Int = 0;
    override fun getLayout(): Int {
        return R.layout.fragment_scan_b
    }

    override fun initView() {
        LineChartSetting.SettingLineChart(lineChart)
    }

    //初始化数据
    override fun initData() {
        //延时操作模仿数据回传
        Timer().schedule(object : TimerTask() {
            override fun run() {
                landList.add(Entry(i.toFloat(), ((-10..10).random()).toFloat()))
                lineDataSet = LineDataSet(landList, "A扫")
                //不绘制数据
                lineDataSet.setDrawValues(false)
                //不绘制圆形指示器
                lineDataSet.setDrawCircles(false)
                //线模式为圆滑曲线（默认折线）
                lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                lineDataSet.setColor(resources.getColor(R.color.theme_color))
                //将数据集添加到数据 ChartData 中
                val lineData = LineData(lineDataSet)
                //将数据添加到图表中
                lineChart.data = lineData
                lineChart.notifyDataSetChanged()
                lineChart.invalidate()
                i++
            }
        }, 0,300)
    }


    //服务器返回数据
    override fun messageResponse(str: String) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
//        iv5.setOnClickListener{
//            "${"123"}".showToast(MyApplication.context)
//        }
    }

}