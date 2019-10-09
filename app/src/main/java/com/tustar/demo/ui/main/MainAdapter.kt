package com.tustar.demo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tustar.demo.R
import com.tustar.demo.ui.main.MainItem.Companion.TYPE_SECTION


class MainAdapter(val items: List<MainItem>,
                  val itemClickListener: OnItemClickListener?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_SECTION -> SectionViewHolder(inflater.inflate(R.layout.item_main_section, parent,
                    false))
            else -> ContentViewHolder(inflater.inflate(R.layout.item_main_content, parent,
                    false))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SectionViewHolder -> {
                holder.bind(items[position] as SectionItem)
            }
            is ContentViewHolder -> {
                holder.bind(items[position] as ContentItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getType()
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sectionName: TextView = itemView.findViewById(R.id.item_main_section_name)

        fun bind(item: SectionItem) {
            sectionName.setText(item.nameResId)
        }
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var contentDesc: TextView = itemView.findViewById(R.id.item_main_content_desc)

        fun bind(item: ContentItem) {
            contentDesc.setText(item.descResId)
            itemView.setOnClickListener { itemClickListener?.onItemClick(item) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: ContentItem)
    }
}