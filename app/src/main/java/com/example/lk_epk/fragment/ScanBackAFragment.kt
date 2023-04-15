package com.example. lk_epk.fragment

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.lk_epk.R
import com.example.lk_epk.activity.MainActivity
import com.example.lk_epk.adapter.BackDataAdapter
import com.example.lk_epk.util.AdapterPositionCallBack
import com.example.lk_epk.util.BaseFragment
import com.example.lk_epk.util.Constant
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.dialog_removecalibration.*
import kotlinx.android.synthetic.main.fragment_scan_backa.*
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File


class ScanBackAFragment : BaseFragment() {
    var selectIndex = 0
    private var pathList = ArrayList<String>()
    private lateinit var dialog : MaterialDialog
    private lateinit var adapter:BackDataAdapter
    private lateinit var filePath:File
    override fun getLayout(): Int {
        return R.layout.fragment_scan_backa
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        val layoutManager = LinearLayoutManager(activityContext)
        recyclerView.layoutManager = layoutManager

        smartRefreshLayout.setRefreshHeader(ClassicsHeader(activityContext))
        smartRefreshLayout.setRefreshFooter(ClassicsFooter(activityContext)) //是否启用下拉刷新功能
        smartRefreshLayout.setOnRefreshListener {
            getFileList()
        }

        imageView.setOnLongClickListener {
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
            dialog.btnRemoveSure.setOnClickListener{
                if (selectIndex==0){
                    //如果只有一条数据删除后显示暂无数据图片，隐藏listview
                    if(pathList.size==1){
                        val file = File("${filePath}/${pathList[selectIndex]}")
                        file.delete()
                        pathList.removeAt(selectIndex)
                        linNoData.visibility = View.VISIBLE
                        linData.visibility = View.GONE
                    }else{
                        val file = File("${filePath}/${pathList[selectIndex]}")
                        file.delete()
                        pathList.removeAt(selectIndex)
                        adapter.notifyDataSetChanged()
                        var path = "${filePath}/${pathList[selectIndex]}"
                        setBitmap(path)
                    }
                }else if (selectIndex==pathList.size-1){
                    val file = File("${filePath}/${pathList[selectIndex]}")
                    file.delete()
                    pathList.removeAt(selectIndex)
                    --selectIndex
                    adapter.setSelectIndex(selectIndex)
                    adapter.notifyDataSetChanged()
                    var path = "${filePath}/${pathList[selectIndex]}"
                    setBitmap(path)
                }else if (selectIndex<pathList.size-1&&selectIndex>0){
                    val file = File("${filePath}/${pathList[selectIndex]}")
                    file.delete()
                    pathList.removeAt(selectIndex)
                    --selectIndex
                    adapter.setSelectIndex(selectIndex)
                    adapter.notifyDataSetChanged()
                    var path = "${filePath}/${pathList[selectIndex]}"
                    setBitmap(path)
                }
                dialog.dismiss()
            }
            true
        }

        ivRef.setOnClickListener {
            getFileList()
            true
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
        filePath = File(Environment.getExternalStorageDirectory().absolutePath + Constant.SCANABACK)
        if (filePath.list().isEmpty()){
            linNoData.visibility = View.VISIBLE
            linData.visibility = View.GONE
        }else{
            if(filePath.list().size>1){
                pathList = filePath.list().toList().reversed() as ArrayList<String>
            }else if (filePath.list().size==1){
                pathList.clear()
                pathList.add(filePath.list()[0])
            }
            linNoData.visibility = View.GONE
            linData.visibility = View.VISIBLE
            adapter = BackDataAdapter(pathList, selectIndex, activity as MainActivity, object :AdapterPositionCallBack{
                override fun backPosition(index: Int) {
                    selectIndex = index
                    var path = "${filePath}/${pathList[index]}"
                    setBitmap(path)
                }
            })
            recyclerView.adapter = adapter
            var path = "${filePath}/${pathList[selectIndex]}"
            setBitmap(path)
            adapter.notifyDataSetChanged()
            smartRefreshLayout?.finishLoadMore()
            smartRefreshLayout?.finishRefresh()
        }
    }

    private fun setBitmap(path:String){
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
                    val bitmap = BitmapFactory.decodeFile(path)
                    imageView.setImageBitmap(bitmap)
                    tvFileName.text = pathList[selectIndex]
                }

                override fun onError(e: Throwable) {

                }
            }).launch()
    }
    //服务器返回数据
    override fun messageResponse(str: String) {
        TODO("Not yet implemented")
    }
}