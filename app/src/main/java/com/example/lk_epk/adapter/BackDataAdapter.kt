package com.example.lk_epk.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lk_epk.R
import com.example.lk_epk.activity.MainActivity
import com.example.lk_epk.util.AdapterPositionCallBack
import java.io.File

internal class BackDataAdapter(
    var dataList: ArrayList<String>,
    var selectIndex: Int,
    var context: MainActivity,
    val adapterPositionCallBack: AdapterPositionCallBack
)  : RecyclerView.Adapter<BackDataAdapter.ViewHolder>() {
    //在内部类里面获取到item里面的组件
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val backItem: LinearLayout = view.findViewById(R.id.backItem)
        val tvName: TextView = view.findViewById(R.id.tvName)
    }
    //重写的第一个方法，用来给制定加载那个类型的Recycler布局
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.adapter_backdata,parent,false)
        var viewHolder=ViewHolder(view)
        //单机事件
        viewHolder.backItem.setOnClickListener {
            var position= viewHolder.layoutPosition
            selectIndex = position
            notifyDataSetChanged()
            adapterPositionCallBack.backPosition(selectIndex)
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = dataList[position]
        if (position==selectIndex){
            holder.tvName.setBackgroundColor(context.resources.getColor(R.color.theme_color))
        }else{
            holder.tvName.setBackgroundColor(context.resources.getColor(R.color.theme_back_color))
        }
    }

    @JvmName("setSelectIndex1")
    fun setSelectIndex(selectIndex1:Int){
        selectIndex = selectIndex1
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}