package com.example.lk_epk.util

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.lk_epk.R
import com.example.lk_epk.activity.MainActivity
import com.example.lk_epk.adapter.BackDataAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.dialog_removecalibration.*
import kotlinx.android.synthetic.main.fragment_scan_back.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File

class ScanBackUtil {
    var selectIndex = 0
    private var pathList = ArrayList<String>()
    private lateinit var dialog : MaterialDialog
    private lateinit var adapter: BackDataAdapter
    private lateinit var filePath:File
    private lateinit var context: Activity
    private lateinit var currentFragment : String
    private lateinit var recyclerView : RecyclerView
    private lateinit var smartRefreshLayout : SmartRefreshLayout
    private lateinit var imageView : ImageView
    private lateinit var floButton : FloatingActionButton
    private lateinit var linNoData : RelativeLayout
    private lateinit var linData : LinearLayout
    private lateinit var tvFileName : TextView
    fun scanBack(activity: FragmentActivity, view: View, tag: String){
        context = activity
        currentFragment = tag

        //闸门
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        smartRefreshLayout = view.findViewById<SmartRefreshLayout>(R.id.smartRefreshLayout)
        imageView = view.findViewById<ImageView>(R.id.imageView)
        floButton = view.findViewById<FloatingActionButton>(R.id.floButton)
        linNoData = view.findViewById<RelativeLayout>(R.id.linNoData)
        linData = view.findViewById<LinearLayout>(R.id.linData)
        tvFileName = view.findViewById<TextView>(R.id.tvFileName)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        smartRefreshLayout.setRefreshHeader(ClassicsHeader(context))
        smartRefreshLayout.setRefreshFooter(ClassicsFooter(context)) //是否启用下拉刷新功能
        smartRefreshLayout.setOnRefreshListener {
            getFileList()
        }

        imageView.setOnLongClickListener {
            dialog = MaterialDialog(context).show{
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

        floButton.setOnClickListener {
            dialog = MaterialDialog(context).show{
                customView(	//自定义弹窗
                    viewRes = R.layout.dialog_projress,//自定义文件
                    dialogWrapContent = true,	//让自定义宽度生效
                    scrollable = true,			//让自定义宽高生效
                    noVerticalPadding = true    //让自定义高度生效
                )
                cornerRadius(16f)  //圆角
            }
            getFileList()
            CoroutineScope(Dispatchers.Main)
                .launch {
                    delay(1500) // 延时1秒
                    dialog.dismiss()
                }
            true
        }
        getFileList()
    }

    //获取数据列表
    private fun getFileList() {
        /**将文件夹下所有文件名存入数组*/
        if (currentFragment=="ScanBackAFragment"){
            filePath = File(Environment.getExternalStorageDirectory().absolutePath + Constant.SCANABACK)
        }else if (currentFragment=="ScanBackBFragment"){
            filePath = File(Environment.getExternalStorageDirectory().absolutePath + Constant.SCANBBACK)
        }

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
            adapter = BackDataAdapter(pathList, selectIndex, context as MainActivity, object :AdapterPositionCallBack{
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
        Luban.with(context)
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
}