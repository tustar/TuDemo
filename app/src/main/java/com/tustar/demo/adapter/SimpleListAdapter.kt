package com.tustar.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by tustar on 11/19/16.
 */

class SimpleListAdapter(var items: List<String> = emptyList()) : RecyclerView.Adapter<SimpleListAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnClickListener { v ->
                itemClickListener?.onItemClick(v, absoluteAdapterPosition)
            }
        }

        fun bind(item: String) {
            title.text = item
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}
