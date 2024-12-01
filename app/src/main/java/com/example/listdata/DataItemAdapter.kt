package com.example.listdata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataItemAdapter(private val dataItems: MutableList<DataItem>) :
    RecyclerView.Adapter<DataItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val indexTextView: TextView = itemView.findViewById(R.id.indexTextView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataItems[position]
        holder.indexTextView.text = item.index.toString()
        holder.titleTextView.text = item.title
        holder.dateTextView.text = item.date
        holder.descriptionTextView.text = item.description
    }

    override fun getItemCount(): Int {
        return dataItems.size
    }

    fun updateList(newItems: List<DataItem>) {
        dataItems.clear()
        dataItems.addAll(newItems)
        notifyDataSetChanged()
    }
}