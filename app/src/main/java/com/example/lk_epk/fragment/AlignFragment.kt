package com.example.lk_epk.fragment

import android.content.Context
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.list.customListAdapter
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.fragment.adapter.MaterialDialogAdapter
import com.example.lk_epk.util.*
import kotlinx.android.synthetic.main.dialog_edittext.*
import kotlinx.android.synthetic.main.dialog_item_header.*
import kotlinx.android.synthetic.main.fragment_scan_a.*


class AlignFragment : BaseFragment(), View.OnClickListener {
    override fun getLayout(): Int {
        return R.layout.fragment_align
    }

    override fun initView() {

    }

    //初始化数据
    override fun initData() {
    }


    //读取返回数据
    override fun backData() {
        TODO("Not yet implemented")
    }
    //写入的数据
    override fun writeData(str: String) {
        LogUtil.i("ScanAFragment",str)
    }

    override fun onClick(v: View?) {
//        iv5.setOnClickListener{
//            "${"123"}".showToast(MyApplication.context)
//        }
    }

}