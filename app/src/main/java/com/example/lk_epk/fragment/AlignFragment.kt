package com.example.lk_epk.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.entity.Calibration
import com.example.lk_epk.fragment.adapter.CalibrationAdapter
import com.example.lk_epk.util.*
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.dialog_addcalibration.*
import kotlinx.android.synthetic.main.dialog_addcalibration.etWorkPipe
import kotlinx.android.synthetic.main.dialog_removecalibration.*
import kotlinx.android.synthetic.main.fragment_align.*


class AlignFragment : BaseFragment(), View.OnClickListener {
    private lateinit var dialog : MaterialDialog
    private lateinit var localList : MutableList<Calibration>
    private var writeList : MutableList<Calibration> = ArrayList()
    private var allList : MutableList<Calibration> = ArrayList()
    private lateinit var recyclerViewAdapter:CalibrationAdapter
    private lateinit var workPipe : String
    private lateinit var temp : String
    private lateinit var land : String

    override fun getLayout(): Int {
        return R.layout.fragment_align
    }

    override fun initView() {
        btnAddCalibration.setOnClickListener(this)
        val linlayoutManager = LinearLayoutManager(activityContext)
        recyclerView.layoutManager = linlayoutManager
        recyclerViewAdapter = CalibrationAdapter(activityContext,allList,object: AdapterCallBack{
            override fun <T> backLongClickData(data: T) {
                val longClientSelectBean = data as Calibration
                dialog = MaterialDialog(activityContext).show{
                    customView(	//自定义弹窗
                        viewRes = R.layout.dialog_removecalibration,//自定义文件
                        dialogWrapContent = true,	//让自定义宽度生效
                        scrollable = true,			//让自定义宽高生效
                        noVerticalPadding = true    //让自定义高度生效
                    )
                    cornerRadius(16f)  //圆角
                }
                dialog.btnRemoveCancel.setOnClickListener{
                    dialog.dismiss()
                }
                dialog.btnRemoveSure.setOnClickListener {
                    removeCaliration(longClientSelectBean)
                }
            }
            override fun <T> backBeanData(data: T) {
                val bean = data as Calibration
                tvHeaderWorkPipe.text = bean.workpiece+"\n"+bean.temp+"m/s"
            }
        })
        recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()
        //组装数据
        makeData()
    }

    private fun makeData(){
        //本地基础数据
        val loadData = FileUtil.getLocalData(activityContext,"calibrationlist.json")
        localList = FileUtil.getGsonData(activityContext,loadData)
        //编辑增加数据
        var jsonCaliration = FileUtil.readFileContent(MyApplication.context.getDir(Constant.ADATA_CALITRATION, 0).absolutePath+"/LKCalitration.json")
        val parser = JsonParser()
        if (jsonCaliration!=null&& jsonCaliration.isNotEmpty()) {
            val jsonArray = parser.parse(jsonCaliration).asJsonArray
            val gson = Gson()
            val it: Iterator<JsonElement> = jsonArray.iterator()
            while (it.hasNext()) {
                val bean = it.next()
                val calibration = gson.fromJson<Any>( bean, Calibration::class.java as Class<Any?>) as Calibration
                writeList.add(calibration)
            }
//            for (i in 0 until  writeList.size){
//                localList.add(writeList[i])
//            }
            allList.addAll(localList)
            allList.addAll(writeList)
            recyclerViewAdapter.notifyDataSetChanged()
        }else{
            allList.addAll(localList)
        }
    }

    //初始化数据
    override fun initData() {
    }

    //服务器返回数据
    override fun messageResponse(str: String) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnAddCalibration -> {
                dialog = MaterialDialog(activityContext).show{
                    customView(	//自定义弹窗
                        viewRes = R.layout.dialog_addcalibration,//自定义文件
                        dialogWrapContent = true,	//让自定义宽度生效
                        scrollable = true,			//让自定义宽高生效
                        noVerticalPadding = true    //让自定义高度生效
                    )
                    cornerRadius(16f)  //圆角
                }
                dialog.btnAddCancel.setOnClickListener{
                    dialog.dismiss()
                }
                dialog.btnAddSure.setOnClickListener {
                    addCaliration()
                }
            }
        }
    }

    //新建材料声速
    private fun addCaliration(){
        if (dialog.etWorkPipe.text.toString().trim { it <= ' ' } == "") {
            "${resources.getString(R.string.write_workpipe)}".showToast(MyApplication.context)
            return
        } else {
            workPipe = dialog.etWorkPipe.text.toString()
        }
        if (dialog.etTemp.text.toString().trim { it <= ' ' } == "") {
            "${resources.getString(R.string.write_temp)}".showToast(MyApplication.context)
            return
        } else {
            temp = dialog.etTemp.text.toString()
        }
        if (dialog.etLand.text.toString().trim { it <= ' ' } == "") {
            "${resources.getString(R.string.write_land)}".showToast(MyApplication.context)
            return
        }else {
            land = dialog.etLand.text.toString()
        }
        val caliration = Calibration(workPipe,temp,land)
        if (allList.contains(caliration)){
            resources.getString(R.string.pipe_have).showToast(MyApplication.context)
            dialog.dismiss()
            return
        }
        writeList.add(caliration)
        allList.add(caliration)
        recyclerViewAdapter.notifyDataSetChanged()

        val fileName = "LKCalitration.json"
        val filePath = FileUtil.creatFile(MyApplication.context.getDir(Constant.ADATA_CALITRATION, 0).absolutePath, fileName)?.path
        val message =  FileUtil.writeData(filePath,Gson().toJson(writeList))
        dialog.dismiss()
        message.showToast(MyApplication.context)
    }

    //删除材料声速
    private fun removeCaliration(longClientSelectBean: Calibration) {
        if (localList.contains(longClientSelectBean)){
            dialog.dismiss()
            resources.getString(R.string.basisc_no_remove).showToast(activityContext)
            return
        }
        val index:Int = writeList.indexOf(longClientSelectBean)
        writeList.removeAt(index)
        val fileName = "LKCalitration.json"
        val filePath = FileUtil.creatFile(MyApplication.context.getDir(Constant.ADATA_CALITRATION, 0).absolutePath, fileName)?.path
        val message =  FileUtil.writeData(filePath,Gson().toJson(writeList))
        dialog.dismiss()
        if (message==activityContext.resources.getString(R.string.save_success)){
            activityContext.resources.getString(R.string.remove_success).showToast(MyApplication.context)
            val index1:Int = allList.indexOf(longClientSelectBean)
            allList.removeAt(index1)
            recyclerViewAdapter.notifyDataSetChanged()
        }
    }
}