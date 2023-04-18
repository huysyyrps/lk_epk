package com.example.lk_epk.fragment

import android.view.View
import com.example.lk_epk.R
import com.example.lk_epk.util.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import java.util.*


class ScanBFragment : BaseFragment(){
//    private lateinit var dialog : MaterialDialog
    private lateinit var btnTag : String
    private lateinit var dataList: MutableList<String>
    private var landList: ArrayList<Entry> = ArrayList()
    private lateinit var lineDataSet:LineDataSet
    private lateinit var fileName:String

    companion object{
        private const val TAG = "ScanBFragment"
    }
    override fun getLayout(): Int {
        return R.layout.fragment_scan
    }

    override fun initView(view: View) {
        BtnClient().btnSetClient(requireActivity(), view, nettyTcpClient,"ScanBFragment")
    }

    //初始化数据
    override fun initData() {
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        if (::dialog.isInitialized){
//            dialog.dismiss()
//        }
//    }

//    override fun messageResponse(str: String) {
//        TODO("Not yet implemented")
//    }
}