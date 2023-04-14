package com.example.lk_epk.fragment

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lk_epk.R
import com.example.lk_epk.adapter.BackDataAdapter
import com.example.lk_epk.entity.BackData
import com.example.lk_epk.util.BaseFragment
import com.example.lk_epk.util.Constant
import com.example.lk_epk.util.LogUtil
import com.example.lk_epk.util.showToast
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_scan_backa.*
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File


class ScanBackAFragment : BaseFragment() {
    private var pathList = ArrayList<String>()
    private var dataList = ArrayList<BackData>()
    private val adapter by lazy { BackDataAdapter(dataList) }
    private val file by lazy { File(Environment.getExternalStorageDirectory().absolutePath + Constant.SCANABACK) }
    private var startNum = 0
    private var lastNum = 50
    private var allNum = 0
    override fun getLayout(): Int {
        return R.layout.fragment_scan_backa
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        val gridLayoutManager = GridLayoutManager(activityContext, 5)
        recyclerView.layoutManager = gridLayoutManager

        smartRefreshLayout.setRefreshHeader(ClassicsHeader(activityContext))
        smartRefreshLayout.setRefreshFooter(ClassicsFooter(activityContext)) //是否启用下拉刷新功能
        smartRefreshLayout.setOnRefreshListener {
            startNum = 0
            lastNum = 50
            dataList.clear()
            getFileList()
        }
        smartRefreshLayout.setOnLoadMoreListener {
            if (allNum == lastNum) {
                "暂无更多数据".showToast(activityContext)
                smartRefreshLayout.finishLoadMore() //结束加载
            } else if (lastNum < allNum) {
                startNum = lastNum
                lastNum += 50
                if (lastNum >= allNum) {
                    lastNum = allNum
                }
                getFileList()
            }
        }

    }

    //初始化数据
    @RequiresApi(Build.VERSION_CODES.O)
    override fun initData() {
        getFileList()
    }

    //获取数据列表
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFileList() {
        /**将文件夹下所有文件名存入数组*/
        pathList = file.list().toList().reversed() as ArrayList<String>
        allNum = pathList.size
        if (pathList.isEmpty()) {
            "暂无图片".showToast(activityContext)
        } else {
            /**遍历数组*/
            if (allNum > 50) {
                for (i in startNum until lastNum) {
                    //val bitmap = BitmapFactory.decodeFile("${file}/${pathList[i]}")
                    var path = "${file}/${pathList[i]}"
                    makeData(i,path)
                }
            }else{
                for (i in startNum until pathList.size) {
                    var path = "${file}/${pathList[i]}"
                    makeData(i,path)
                }
                lastNum = allNum
                smartRefreshLayout.finishLoadMore() //结束加载
            }
        }
    }

    private fun makeData(i:Int, path:String){
        Luban.with(activityContext)
            .load(path)
            .ignoreBy(100)
            .filter { path ->
                !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))
            }
            .setCompressListener(object : OnCompressListener {
                override fun onStart() {
                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                }

                override fun onSuccess(file: File) {
                    val bitmap = BitmapFactory.decodeFile(file.path)
                    var backData = BackData(pathList[i], bitmap)
                    dataList.add(backData)
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {

                }
            }).launch()
        smartRefreshLayout?.finishLoadMore()
        smartRefreshLayout?.finishRefresh()
    }


    //服务器返回数据
    override fun messageResponse(str: String) {
        TODO("Not yet implemented")
    }
}