package com.example.lk_epk.fragment

import android.view.View
import com.example.lk_epk.R
import com.example.lk_epk.util.*


class ScanBFragment : BaseFragment(), View.OnClickListener {
    override fun getLayout(): Int {
        return R.layout.fragment_scan_b
    }

    override fun initView() {

    }

    //初始化数据
    override fun initData() {
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