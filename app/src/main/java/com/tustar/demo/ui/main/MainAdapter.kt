package com.tustar.demo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tustar.demo.databinding.ItemMainChildBinding
import com.tustar.demo.databinding.ItemMainGroupBinding
import com.tustar.demo.ui.main.MainItem.Companion.TYPE_SECTION


class MainAdapter()
    : ListAdapter<MainItem, RecyclerView.ViewHolder>(MainDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_SECTION -> GroupViewHolder(ItemMainGroupBinding.inflate(inflater, parent,
                    false))
            else -> ChildViewHolder(ItemMainChildBinding.inflate(inflater, parent,
                    false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GroupViewHolder -> {
                holder.bind(getItem(position) as GroupItem)
            }
            is ChildViewHolder -> {
                holder.bind(getItem(position) as ChildItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getType()
    }

    inner class GroupViewHolder(private val binding: ItemMainGroupBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GroupItem) {
            binding.apply {
                group = item
                executePendingBindings()
            }
        }
    }

    inner class ChildViewHolder(private val binding: ItemMainChildBinding)
        : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {view ->
                binding.child?.let { child ->
                    view.findNavController().navigate(child.direction)
                }
            }
        }

        fun bind(item: ChildItem) {
            binding.apply {
                child = item
                executePendingBindings()
            }
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