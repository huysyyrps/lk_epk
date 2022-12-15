package com.example.lk_epk.fragment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.entity.Calibration
import com.example.lk_epk.util.AdapterCallBack
import com.example.lk_epk.util.DialogCallBack
import com.example.lk_epk.view.BaseButton
import kotlinx.android.synthetic.main.adapter_calibration.view.*
import java.io.File

class CalibrationAdapter (context: Context, private val placeList: MutableList<Calibration>, private val adapterCallBack: AdapterCallBack) : RecyclerView.Adapter<CalibrationAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvWorkPiece: TextView = view.findViewById(R.id.tvWorkPiece)
        val tvTemp: TextView = view.findViewById(R.id.tvTemp)
        val tvLand: TextView = view.findViewById(R.id.tvLand)
        val linCaliation: LinearLayout = view.findViewById(R.id.linCaliation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_calibration, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var bean = placeList[position]
        holder.tvWorkPiece.text = bean.workpiece
        holder.tvTemp.text = bean.temp
        holder.tvLand.text = bean.land
        if(position%2!=0){
            holder.linCaliation.setBackgroundColor(MyApplication.context.resources.getColor(R.color.viewfinder_mask))
        }
        holder.linCaliation.setOnClickListener {
            adapterCallBack.backBeanData(bean)
        }
        holder.linCaliation.setOnLongClickListener {
            adapterCallBack.backLongClickData<Calibration>(bean)
            true
        }
    }

    override fun getItemCount() = placeList.size
}