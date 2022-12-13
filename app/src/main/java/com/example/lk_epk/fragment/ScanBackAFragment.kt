package com.example.lk_epk.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.list.customListAdapter
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.entity.ScanABean
import com.example.lk_epk.fragment.adapter.MaterialDialogAdapter
import com.example.lk_epk.util.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_scan_backa.*
import kotlinx.android.synthetic.main.fragment_scan_backa.btnAudioSpeed
import kotlinx.android.synthetic.main.fragment_scan_backa.btnGate
import kotlinx.android.synthetic.main.fragment_scan_backa.btnLeave
import kotlinx.android.synthetic.main.fragment_scan_backa.btnMaterialType
import kotlinx.android.synthetic.main.fragment_scan_backa.btnRangeAdd
import kotlinx.android.synthetic.main.fragment_scan_backa.btnWaveType
import kotlinx.android.synthetic.main.fragment_scan_backa.btnWorkTemp
import kotlinx.android.synthetic.main.fragment_scan_backa.lineChart
import java.io.File
import kotlin.collections.ArrayList


class ScanBackAFragment : BaseFragment(), View.OnClickListener {
    private val fileList = ArrayList<File>()
    private var landList : ArrayList<Entry> = ArrayList()
    private lateinit var dialog : MaterialDialog
    override fun getLayout(): Int {
        return R.layout.fragment_scan_backa
    }

    override fun initView() {
        btnBackList.setOnClickListener(this)
        LineChartSetting.SettingLineChart(lineChart)
    }

    //初始化数据
    @RequiresApi(Build.VERSION_CODES.O)
    override fun initData() {
        getFileList()
    }

    //获取数据列表
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFileList(){
        val filePath : String = MyApplication.context.getDir(Constant.ADATA_PATH, 0).toPath().toString()
        if (FileUtil.isFileExists(filePath) && FileUtil.hasFileExists(filePath)) {
            FileUtil.getFilesAllName(fileList, filePath)
        }
        val adapter = MaterialDialogAdapter("backData",fileList, object:DialogCallBack{
            override fun backData(data: String) {
                val jsonString = FileUtil.readFileContent(data)
                val myGson = Gson()
                val scanBean = myGson.fromJson(jsonString,ScanABean::class.java)
                setScanBean(scanBean)
                dialog?.dismiss()
            }
        })
        dialog = MaterialDialog(activityContext).show{
            customView(	//自定义弹窗
                viewRes = R.layout.dialog_item_header,//自定义文件
                dialogWrapContent = true,	//让自定义宽度生效
                scrollable = true,			//让自定义宽高生效
                noVerticalPadding = true    //让自定义高度生效
            )
            cornerRadius(16f)  //圆角
            customListAdapter(adapter)
        }
    }

    //显示数据
    private fun setScanBean(scanBean: ScanABean) {
        btnGate.text = scanBean.gate
        btnLeave.text = scanBean.leave
        btnMaterialType.text = scanBean.materialType
        btnAudioSpeed.text = scanBean.audioSpeed
        btnWaveType.text = scanBean.waveType
        btnRangeAdd.text = scanBean.rangeAdd
        btnWorkTemp.text = scanBean.workTemp

        landList = scanBean.landList
        var lineDataSet = LineDataSet(landList, "A扫")
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
        lineChart.setData(lineData)
        lineChart.invalidate()
    }

    //读取返回数据
    override fun backData() {
        TODO("Not yet implemented")
    }
    //写入的数据
    override fun writeData(str: String) {
        LogUtil.i("ScanAFragment",str)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when(v?.id){
            //闸门
            R.id.btnBackList -> {
                getFileList()
            }
            else -> {"111"}
        }
    }

}