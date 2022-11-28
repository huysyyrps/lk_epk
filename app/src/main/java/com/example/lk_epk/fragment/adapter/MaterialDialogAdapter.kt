package com.example.lk_epk.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lk_epk.R
import com.example.lk_epk.util.DialogCallBack
import java.util.*

class MaterialDialogAdapter(private val placeList: List<String>, private val dialogCallBack: DialogCallBack) : RecyclerView.Adapter<MaterialDialogAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvData: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dialog_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.tvData.text = place
        holder.tvData.setOnClickListener {
            dialogCallBack.backData(place)
        }
    }

    override fun getItemCount() = placeList.size
}