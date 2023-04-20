package com.example. lk_epk.fragment

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.afollestad.materialdialogs.MaterialDialog
import com.example.lk_epk.R
import com.example.lk_epk.adapter.BackDataAdapter
import com.example.lk_epk.util.BaseFragment
import com.example.lk_epk.util.ScanBackUtil
import java.io.File


class ScanBackAFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fragment_scan_back
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView(view: View) {
        ScanBackUtil().scanBack(requireActivity(),view,"ScanBackAFragment")
    }

    //初始化数据
    @RequiresApi(Build.VERSION_CODES.O)
    override fun initData() {
    }
}