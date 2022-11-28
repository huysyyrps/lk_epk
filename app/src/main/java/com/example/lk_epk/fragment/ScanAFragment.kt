package com.example.lk_epk.fragment

import android.content.Context
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.list.customListAdapter
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.fragment.adapter.MaterialDialogAdapter
import com.example.lk_epk.util.BaseFragment
import com.example.lk_epk.util.Constant
import com.example.lk_epk.util.DialogCallBack
import com.example.lk_epk.util.showToast
import kotlinx.android.synthetic.main.dialog_edittext.*
import kotlinx.android.synthetic.main.dialog_item_header.*
import kotlinx.android.synthetic.main.fragment_scan_a.*

class ScanAFragment : BaseFragment(), View.OnClickListener {
    private lateinit var activityContext : Context
    private lateinit var dialog : MaterialDialog
    private lateinit var btnTag : String
    private lateinit var dataList: List<String>
    override fun getLayout(): Int {
        return R.layout.fragment_scan_a
    }

    override fun initView() {
        //闸门
        btnGate.setOnClickListener(this)
        //平均等级
        btnLeave.setOnClickListener(this)
        //材料类型
        btnMaterialType.setOnClickListener(this)
        //波形方式
        btnWaveType.setOnClickListener(this)
        //范围扩展
        btnRangeAdd.setOnClickListener(this)
        //工作温度
        btnWorkTemp.setOnClickListener(this)
        //参数初始化
        btnInitialize.setOnClickListener(this)
    }

    fun getActivityContext(context: Context) {
        activityContext = context
    }

    //初始化数据
    override fun initData() {
    }

    override fun onClick(v: View?){
        when(v?.id){
            //闸门
            R.id.btnGate -> {
                dataList = resources.getStringArray(R.array.GATE).toList<String>()
                btnTag = Constant.GATE
            }
            //平均等级
            R.id.btnLeave -> {
                dataList = resources.getStringArray(R.array.LEAVE).toList<String>()
                btnTag = Constant.LEAVE
            }
            //材料类型
            R.id.btnMaterialType -> {
                dataList = resources.getStringArray(R.array.MATERIALTYPE).toList<String>()
                btnTag = Constant.MATERIALTYPE
            }
            //检波方式
            R.id.btnWaveType -> {
                dataList = resources.getStringArray(R.array.WAVETYPE).toList<String>()
                btnTag = Constant.WAVETYPE
            }
            //扩展范围
            R.id.btnRangeAdd -> {
                dataList = resources.getStringArray(R.array.RANGEADD).toList<String>()
                btnTag = Constant.RANGEADD
            }
            //初始化
            R.id.btnInitialize -> {
                "${"初始化"}".showToast(MyApplication.context)
                return
            }
            //工作温度
            R.id.btnWorkTemp -> {
                dialog = MaterialDialog(activityContext).show{
                    customView(	//自定义弹窗
                        viewRes = R.layout.dialog_edittext,//自定义文件
                        dialogWrapContent = true,	//让自定义宽度生效
                        scrollable = true,			//让自定义宽高生效
                        noVerticalPadding = true    //让自定义高度生效
                    )
                    cornerRadius(16f)  //圆角
                }
                dialog.btnCancel.setOnClickListener{
                    dialog.dismiss()
                }
                dialog.btnSure.setOnClickListener {
                    val temp = dialog.editText.text.toString()
                    if (temp.trim { it <= ' ' } == ""){
                        "${resources.getString(R.string.work_temp)}".showToast(MyApplication.context)
                        return@setOnClickListener
                    }else if (temp.toDouble()<-200||temp.toDouble()>800){
                        "${resources.getString(R.string.right_write_work_temp)}".showToast(MyApplication.context)
                        return@setOnClickListener
                    }else{
                        dialog.dismiss()
                        sendData("11",dialog.editText.text.toString())
                        return@setOnClickListener
                    }
                }
                return
            }
            else -> {"111"}
        }
        btnSelect()
    }

    /**
     * 点击事件
     * btnTag:点击的哪个按钮
     */
    private fun btnSelect() {
        val adapter = MaterialDialogAdapter(dataList, object:DialogCallBack{
            override fun backData(data: String) {
                when(btnTag){
                    Constant.GATE -> {
                        btnGate.text = data
                        sendData("1",data)
                    }
                    Constant.LEAVE -> {
                        btnLeave.text = data
                        sendData("2",data)
                    }
                    Constant.MATERIALTYPE -> {
                        btnMaterialType.text = data?.split(" ")[0]
                        btnAudioSpeed.text = data?.split(" ")[1]
                        sendData("3",data)
                    }
                    Constant.WAVETYPE -> {
                        btnWaveType.text = data
                        sendData("4",data)
                    }
                    Constant.RANGEADD -> {
                        btnRangeAdd.text = data
                        sendData("5",data)
                    }
                    else -> {"111"}
                }
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
        if(dialog.tvTitle!=null){
            dialog.tvTitle.text = resources.getText(R.string.device_power).toString()
        }
    }

    //发送数据
    private fun sendData(s: String, data: String) {
        data.showToast(MyApplication.context)
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog.dismiss()
    }
}