package com.tustar.demo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.tustar.demo.R
import com.tustar.demo.ui.main.MainItem.Companion.TYPE_SECTION


class MainAdapter(var items: List<MainItem>,
                  val itemClickListener: OnItemClickListener?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_SECTION -> GroupViewHolder(inflater.inflate(R.layout.item_main_group, parent,
                    false))
            else -> ChildViewHolder(inflater.inflate(R.layout.item_main_child, parent,
                    false))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GroupViewHolder -> {
                holder.bind(items[position] as GroupItem)
            }
            is ChildViewHolder -> {
                holder.bind(items[position] as ChildItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getType()
    }

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.item_main_group_name)

        fun bind(item: GroupItem) {
            name.setText(item.nameResId)
        }
    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.item_main_child_name)
        private val arrow: AppCompatImageView = itemView.findViewById(
                R.id.item_main_child_arrow)

        fun bind(item: ChildItem) {
            name.setText(item.descResId)
            itemView.setOnClickListener { itemClickListener?.onItemClick(item) }
            arrow.visibility = if (item.isMenu) View.VISIBLE else View.GONE
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: ChildItem)
    }
}