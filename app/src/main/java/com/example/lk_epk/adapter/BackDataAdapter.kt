package com.example.lk_epk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lk_epk.R
import com.example.lk_epk.entity.BackData
import java.io.File

internal class BackDataAdapter(var dataList:List<BackData> )  : RecyclerView.Adapter<BackDataAdapter.ViewHolder>() {
    //在内部类里面获取到item里面的组件
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val tvName: TextView = view.findViewById(R.id.tvName)
    }
    //重写的第一个方法，用来给制定加载那个类型的Recycler布局
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.adapter_backdata,parent,false)
        var viewHolder=ViewHolder(view)
        //单机事件
        viewHolder.itemView.setOnClickListener {
            var position= viewHolder.adapterPosition
            var news=dataList[position]
        }
        return viewHolder
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.imageView.setImageBitmap(item.bitmap)
        var file = File(item.path)
        holder.tvName.text = file.name
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}