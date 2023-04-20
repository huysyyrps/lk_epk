package com.example.lk_epk.fragment

import android.view.View
import com.example.lk_epk.R
import com.example.lk_epk.util.*


class ScanBackBFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fragment_scan_back
    }

    override fun initView(view: View) {
        ScanBackUtil().scanBack(requireActivity(),view,"ScanBackBFragment")
    }

    //初始化数据
    override fun initData() {
    }
}