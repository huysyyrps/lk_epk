package com.example.lk_epk.fragment

import android.view.LayoutInflater
import android.view.View
import com.example.lk_epk.R
import com.example.lk_epk.util.*

class ScanAFragment : BaseFragment() {

    override fun getLayout(): Int {
        return R.layout.fragment_scan
    }

    override fun initView(view: View) {
        ScanUtil().btnSetClient(requireActivity(), view, nettyTcpClient, "ScanAFragment")
    }

    //初始化数据
    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
//        if (::dialog.isInitialized){
//            dialog.dismiss()
//        }
    }
}