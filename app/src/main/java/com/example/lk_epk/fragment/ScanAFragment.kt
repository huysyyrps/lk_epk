package com.example.lk_epk.fragment

import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.example.lk_epk.R
import com.example.lk_epk.util.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import java.util.*

//, View.OnClickListener, NettyClientListener<String>
class ScanAFragment : BaseFragment() {
    private lateinit var dialog : MaterialDialog
    private lateinit var btnTag : String
    private lateinit var dataList: MutableList<String>
    private var landList: ArrayList<Entry> = ArrayList()
    private lateinit var lineDataSet:LineDataSet
    private lateinit var fileName:String

    companion object{
        private const val TAG = "ScanAFragment"
    }
    override fun getLayout(): Int {
        return R.layout.fragment_scan
    }

    override fun initView(view: View) {
        BtnClient().btnSetClient(requireActivity(), view, nettyTcpClient,"ScanAFragment")
//        //闸门
//        btnGate.setOnClickListener(this)
//        //平均等级
//        btnLeave.setOnClickListener(this)
//        //材料类型
//        btnMaterialType.setOnClickListener(this)
//        //波形方式
//        btnWaveType.setOnClickListener(this)
//        //范围扩展
//        btnRangeAdd.setOnClickListener(this)
//        //工作温度
//        btnWorkTemp.setOnClickListener(this)
//        //参数初始化
//        btnInitialize.setOnClickListener(this)
//        //存储
//        btnSave.setOnClickListener(this)
//        LineChartSetting.SettingLineChart(lineChart)
//
//        swAutoAdd.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked){
//                linSeekBar.visibility = View.VISIBLE
//            } else{
//                linSeekBar.visibility = View.GONE
//            }
//        }
//        //增益设置
//        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                tvSeekBar.text = "" + (seekbar.progress)
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                Log.e("XXX", "${seekBar?.progress}");
//            }
//
//        })
//
//        webView.settings.javaScriptEnabled = true
//        webView.webViewClient = WebViewClient()
//        webView.setBackgroundColor(resources.getColor(R.color.theme_back_color))
//        var webSettings = webView!!.settings
//        // 设置自适应屏幕, 两者合用
//        webSettings.useWideViewPort = true  // 将图片调整到适合webview的大小
//        webSettings.loadWithOverviewMode = true  // 缩放至屏幕的大小
//        webView.loadUrl(Constant.BASE_URL)
//        rockerView// 设置默认状态下的摇杆颜色
//            .setDefaultColor(Color.WHITE)
//            // 设置按下状态下的摇杆颜色
//            .setTouchedColor(Color.WHITE)
//            // 设置背景着色
//            .setBackgroundTint(resources.getColor(R.color.theme_color))
//            // 设置摇杆改变监听
//            .setOnSteeringWheelChangedListener { linearSpeed, angleSpeed ->
//                Log.e("RockerView", "linearSpeed: $linearSpeed, angleSpeed: $angleSpeed")
//            }
    }

    //初始化数据
    override fun initData() {
//        //延时操作模仿数据回传
//        Timer().schedule(object : TimerTask() {
//            override fun run() {
////                makeData()
//                landList.clear()
//                landList.add(Entry(0.toFloat(), 100.toFloat()))
//                landList.add(Entry(1.toFloat(), 2.toFloat()))
//                landList.add(Entry(2.toFloat(), 90.toFloat()))
//                landList.add(Entry(3.toFloat(), 2.toFloat()))
//                for(i in 4..300){
//                    landList.add(Entry(i.toFloat(), ((30..50).random()).toFloat()))
//                }
//
//                lineDataSet = LineDataSet(landList, "A扫")
//                //不绘制数据
//                lineDataSet.setDrawValues(false)
//                //不绘制圆形指示器
//                lineDataSet.setDrawCircles(false)
//                //线模式为圆滑曲线（默认折线）
//                lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
//                lineDataSet.setColor(resources.getColor(R.color.theme_color))
//                //将数据集添加到数据 ChartData 中
//                val lineData = LineData(lineDataSet)
//                //将数据添加到图表中
//                lineChart.setData(lineData)
//                lineChart.notifyDataSetChanged()
//                lineChart.invalidate()
//            }
//        }, 0,150)
    }

//    override fun onClick(v: View?){
//        when(v?.id){
//            //闸门
//            R.id.btnGate -> {
//                dataList = resources.getStringArray(R.array.GATE).toMutableList()
//                btnTag = Constant.GATE
//            }
//            //平均等级
//            R.id.btnLeave -> {
//                dataList = resources.getStringArray(R.array.LEAVE).toMutableList()
//                btnTag = Constant.LEAVE
//            }
//            //材料类型
//            R.id.btnMaterialType -> {
//                dataList = FileUtil.selectMaterialType()
//                btnTag = Constant.MATERIALTYPE
//            }
//            //检波方式
//            R.id.btnWaveType -> {
//                dataList = resources.getStringArray(R.array.WAVETYPE).toMutableList()
//                btnTag = Constant.WAVETYPE
//            }
//            //扩展范围
//            R.id.btnRangeAdd -> {
//                dataList = resources.getStringArray(R.array.RANGEADD).toMutableList()
//                btnTag = Constant.RANGEADD
//            }
//            //初始化
//            R.id.btnInitialize -> {
//                "${"初始化"}".showToast(MyApplication.context)
//                return
//            }
//            //工作温度
//            R.id.btnWorkTemp -> {
//                dialog = setEdittextdialog("WorkTemp")
//                dialog.btnSure.setOnClickListener {
//                    val temp = dialog.editText.text.toString()
//                    if (temp.trim { it <= ' ' } == ""){
//                        "${resources.getString(R.string.work_temp)}".showToast(MyApplication.context)
//                        return@setOnClickListener
//                    }else if (temp.toDouble()<-200||temp.toDouble()>800){
//                        "${resources.getString(R.string.right_write_work_temp)}".showToast(MyApplication.context)
//                        return@setOnClickListener
//                    }else{
//                        dialog.dismiss()
//                        sendData("11",dialog.editText.text.toString())
//                        return@setOnClickListener
//                    }
//                }
//                return
//            }
//            //存储
//            R.id.btnSave ->{
//                dialog = setEdittextdialog("Save")
//                dialog.btnSureString.setOnClickListener {
//                    val name = dialog.editTextString.text.toString()
//                    fileName = if (name.trim { it <= ' ' } == ""){
//                        DateTimeUtil.getNowDateTime()
//                    }else{
//                        name
//                    }
//                    val dir = Environment.getExternalStorageDirectory().absolutePath+Constant.SCANABACK
//                    var saveTag = ScreenCaptureUtils.createInstance()
//                        .setImageName("${fileName}.png")
//                        .setImagePath(dir)
//                        .setView(frameLayout)
//                        .saveBitmapToJpg(100);
//                    if (saveTag==null){
//                        dialog.dismiss()
//                    }else{
//                        activity?.let { it1 ->
//                            resources.getString(R.string.save_success).showToast(
//                                it1
//                            )
//                        }
//                        dialog.dismiss()
//                    }
//                }
//                return
//            }
//            else -> {"111"}
//        }
//        btnSelect()
//    }
//
//    /**
//     * 点击事件
//     * btnTag:点击的哪个按钮
//     */
//    private fun btnSelect() {
//        val adapter = MaterialDialogAdapter("scan", dataList, object:DialogCallBack{
//            override fun backData(data: String) {
//                when(btnTag){
//                    Constant.GATE -> {
//                        btnGate.text = data
//                        sendData("1",data)
//                    }
//                    Constant.LEAVE -> {
//                        btnLeave.text = data
//                        sendData("2",data)
//                    }
//                    Constant.MATERIALTYPE -> {
//                        btnMaterialType.text = data?.split(" ")[0]
//                        btnAudioSpeed.text = data?.split(" ")[1]
//                        sendData("3",data)
//                    }
//                    Constant.WAVETYPE -> {
//                        btnWaveType.text = data
//                        sendData("4",data)
//                    }
//                    Constant.RANGEADD -> {
//                        btnRangeAdd.text = data
//                        sendData("5",data)
//                    }
//                    else -> {"111"}
//                }
//                dialog?.dismiss()
//            }
//        })
//        dialog = MaterialDialog(activityContext).show{
//            customView(	//自定义弹窗
//                viewRes = R.layout.dialog_item_header,//自定义文件
//                dialogWrapContent = true,	//让自定义宽度生效
//                scrollable = true,			//让自定义宽高生效
//                noVerticalPadding = true    //让自定义高度生效
//            )
//            cornerRadius(16f)  //圆角
//            customListAdapter(adapter)
//        }
//        if(dialog.tvTitle!=null){
//            dialog.tvTitle.text = resources.getText(R.string.app_name).toString()
//        }
//    }

//    /**
//     * 设置设置edittextdialog
//     */
//
//    private fun setEdittextdialog(tag:String):MaterialDialog{
//        dialog = MaterialDialog(activityContext).show{
//            customView(	//自定义弹窗
//                viewRes = if (tag=="WorkTemp") R.layout.dialog_numedittext else R.layout.dialog_stringedittext,//自定义文件
//                dialogWrapContent = true,	//让自定义宽度生效
//                scrollable = true,			//让自定义宽高生效
//                noVerticalPadding = true    //让自定义高度生效
//            )
//            cornerRadius(16f)  //圆角
//        }
//        if (tag=="WorkTemp"){
//            dialog.btnCancel.setOnClickListener{
//                dialog.dismiss()
//            }
//        }else{
//            dialog.btnCancelString.setOnClickListener{
//                dialog.dismiss()
//            }
//        }
//        return dialog
//    }
//
//    //发送数据
//    private fun sendData(s: String, data: String) {
//        data.showToast(MyApplication.context)
//        if (!nettyTcpClient.connectStatus) {
//            R.string.no_connect.showToast(MyApplication.context)
//        } else {
//            nettyTcpClient.sendMsgToServer("[CC AB CD 12 12]", object : MessageStateListener {
//                override fun isSendSuccss(isSuccess: Boolean) {
//                    if (isSuccess) {
//                        Log.d(TAG, "Write auth successful")
//                    } else {
//                        Log.d(TAG, "Write auth error")
//                    }
//                }
//            })
//        }
//    }

    //服务器返回数据
//    override fun messageResponse(str: String) {
//        LogUtil.i(TAG,str)
//        val data = arrayOf(13,-20,15,-7,0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,15,13,-20,15,-7,
//            0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,15,13,-20,15,-7,0,0,0,0,2,27,15,-7,15,-7,0,0,
//            0,0,2,-5,15,-7,15,13,-20,15,-7,0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,15,13,-20,15,
//            -7,0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,15,13,-20,15,-7,0,0,0,0,2,27,15,-7,15,-7,
//            0,0,0,0,2,-5,15,-7,15,13,-20,15,-7,0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,15,13,-20,
//            15,-7,0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,15,13,-20,15,-7,0,0,0,0,2,27,15,-7,15,
//            -7,0,0,0,0,2,-5,15,-7,15,13,-20,15,-7,0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,15,13,
//            -20,15,-7,0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,15,13,-20,15,-7,0,0,0,0,2,27,15,-7,
//            15,-7,0,0,0,0,2,-5,15,-7,15,13,-20,15,-7,0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,15,
//            13,-20,15,-7,0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,15,13,-20,15,-7,0,0,0,0,2,27,15,
//            -7,15,-7,0,0,0,0,2,-5,15,-7,15,13,-20,15,-7,0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,
//            15,13,-20,15,-7,0,0,0,0,2,27,15,-7,15,-7,0,0,0,0,2,-5,15,-7,15)
//        landList.clear()
//        for(i in 0 until  data.size){
////            landList.removeAt(i)
//            landList.add(Entry(i.toFloat(), data[i].toFloat()))
//        }
//        lineDataSet = LineDataSet(landList, "")
//        //将数据集添加到数据 ChartData 中
//        val lineData = LineData(lineDataSet)
//        //将数据添加到图表中
//        lineChart.data = lineData
//        lineChart.notifyDataSetChanged()
//        lineChart.invalidate()
//    }

//    /**
//     * 录屏申请
//     */
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        //截屏前的准备
//    }

    override fun onDestroy() {
        super.onDestroy()
//        if (::dialog.isInitialized){
//            dialog.dismiss()
//        }
    }
}