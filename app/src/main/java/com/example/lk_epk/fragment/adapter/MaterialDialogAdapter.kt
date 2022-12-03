package com.example.lk_epk.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lk_epk.R
import com.example.lk_epk.util.DialogCallBack
import com.example.lk_epk.view.BaseButton
import java.io.File
import java.util.*

class MaterialDialogAdapter<T>(private val tag: String, private val placeList: List<T>, private val dialogCallBack: DialogCallBack) : RecyclerView.Adapter<MaterialDialogAdapter<T>.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bsButton: BaseButton = view.findViewById(R.id.bsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dialog_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var place = ""
        if (tag == "backData"){
            var file = File(placeList[position].toString())
            place = placeList[position].toString()
            holder.bsButton.text = file.name.toString()
        }else {
            place = placeList[position].toString()
            holder.bsButton.text = place
        }
        holder.bsButton.setOnClickListener {
            dialogCallBack.backData(place)
        }
    }

    override fun getItemCount() = placeList.size
}