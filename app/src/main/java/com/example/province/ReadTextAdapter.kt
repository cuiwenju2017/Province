package com.example.province

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReadTextAdapter(private var items: List<ProvinceBean>) :
    RecyclerView.Adapter<ReadTextAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val ll: LinearLayout = itemView.findViewById(R.id.ll)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    lateinit var onItemClick: (v: View, pos: Int) -> Unit

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listdata = items[position]
        holder.name.text = listdata.name

        holder.ll.setOnClickListener {
            onItemClick.invoke(it, position)
        }
    }
}