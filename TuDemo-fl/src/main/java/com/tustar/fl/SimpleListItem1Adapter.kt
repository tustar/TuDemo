package com.tustar.fl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by tustar on 11/19/16.
 */

class SimpleListItem1Adapter(var data: List<String>?) : RecyclerView.Adapter<SimpleListItem1Adapter.ViewHolder>() {

    private var mItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mText!!.text = data?.get(position)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mItemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mText: TextView? = null

        init {
            mText = itemView.findViewById(android.R.id.text1) as TextView
            itemView.setOnClickListener { v ->
                mItemClickListener!!.onItemClick(v, adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}
