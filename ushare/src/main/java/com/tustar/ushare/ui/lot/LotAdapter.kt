package com.tustar.ushare.ui.lot

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tustar.ushare.R
import com.tustar.ushare.data.bean.User
import org.jetbrains.anko.find


class LotAdapter(private var users: MutableList<User>) : RecyclerView.Adapter<LotAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.ushare_item_lot, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindUser(users[position])
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }


    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private lateinit var nick: TextView
        private lateinit var mobile: TextView
        private lateinit var weight: TextView

        init {
            itemView?.apply {
                nick = find(R.id.lot_nick_text)
                mobile = find(R.id.lot_mobile_text)
                weight = find(R.id.lot_weight_text)
                setOnClickListener {
                    itemClickListener?.onItemClick(it, adapterPosition)
                }
            }
        }

        fun bindUser(user: User) {
            nick.text = user.nick
            mobile.text = user.mobile
            weight.text = user.weight.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}