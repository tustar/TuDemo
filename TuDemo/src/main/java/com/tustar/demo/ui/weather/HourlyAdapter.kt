package com.tustar.demo.ui.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tustar.demo.R
import com.tustar.demo.data.remote.Hourly
import com.tustar.demo.databinding.ItemHourlyBinding
import com.tustar.ktx.getDrawableByName
import java.text.SimpleDateFormat


class HourlyAdapter() : ListAdapter<Hourly, RecyclerView.ViewHolder>(HourlyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HourlyViewHolder(ItemHourlyBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HourlyViewHolder) {
            val item = getItem(position)
            if (item is Hourly) {
                holder.bind(item)
            }
        }
    }


    class HourlyViewHolder(private val binding: ItemHourlyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {

            }
        }

        @SuppressLint("SimpleDateFormat")
        fun bind(item: Hourly) {
            binding.apply {
                val context = binding.root.context
                val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mmXXX").parse(item.fxTime)
                hourTime.text = SimpleDateFormat("HH:mm").format(date)
                hourSky.setImageDrawable(context.getDrawableByName("w_${item.icon}"))
                hourTemp.text = context.getString(R.string.weather_temp, item.temp)
            }
        }
    }

    private class HourlyDiffCallback : DiffUtil.ItemCallback<Hourly>() {

        override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem == newItem
        }
    }
}