package com.tustar.demo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tustar.demo.data.model.DemoItem
import com.tustar.demo.data.model.GroupItem
import com.tustar.demo.data.model.MainItem
import com.tustar.demo.data.model.MainItem.Companion.TYPE_GROUP
import com.tustar.demo.databinding.ItemHomeChildBinding
import com.tustar.demo.databinding.ItemHomeGroupBinding


class HomeAdapter()
    : ListAdapter<MainItem, RecyclerView.ViewHolder>(MainDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_GROUP -> GroupViewHolder(ItemHomeGroupBinding.inflate(inflater, parent,
                    false))
            else -> ChildViewHolder(ItemHomeChildBinding.inflate(inflater, parent,
                    false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GroupViewHolder -> {
                holder.bind(getItem(position) as GroupItem)
            }
            is ChildViewHolder -> {
                holder.bind(getItem(position) as DemoItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getType()
    }

    inner class GroupViewHolder(private val binding: ItemHomeGroupBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GroupItem) {

        }
    }

    inner class ChildViewHolder(private val binding: ItemHomeChildBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(demoItem: DemoItem) {

        }
    }

    private class MainDiffCallback : DiffUtil.ItemCallback<MainItem>() {

        override fun areItemsTheSame(oldItem: MainItem, newItem: MainItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MainItem, newItem: MainItem): Boolean {
            return oldItem == newItem
        }
    }
}