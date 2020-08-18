package com.tustar.demo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tustar.demo.data.Demo
import com.tustar.demo.data.Group
import com.tustar.demo.data.MainItem
import com.tustar.demo.data.MainItem.Companion.TYPE_GROUP
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
                holder.bind(getItem(position) as Group)
            }
            is ChildViewHolder -> {
                holder.bind(getItem(position) as Demo)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getType()
    }

    inner class GroupViewHolder(private val binding: ItemHomeGroupBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Group) {
            binding.apply {
                group = item
                executePendingBindings()
            }
        }
    }

    inner class ChildViewHolder(private val binding: ItemHomeChildBinding)
        : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.child?.let { child ->
                    view.findNavController().navigate(child.actionId)
                }
            }
        }

        fun bind(item: Demo) {
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