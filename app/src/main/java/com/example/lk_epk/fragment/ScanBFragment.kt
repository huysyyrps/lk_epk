package com.example.lk_epk.fragment

import android.view.View
import com.example.lk_epk.R
import com.example.lk_epk.util.*
import kotlinx.android.synthetic.main.scan_right.*


class ScanBFragment : BaseFragment(){

    companion object{
        private const val TAG = "ScanBFragment"
    }
    override fun getLayout(): Int {
        return R.layout.fragment_scan
    }

    override fun initView(view: View) {
        linFirstLine.visibility = View.GONE
        ScanUtil().btnSetClient(requireActivity(), view, nettyTcpClient,  "ScanBFragment")
    }

    //初始化数据
    override fun initData() {
    }
}