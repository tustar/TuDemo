package com.tustar.demo.module.jet

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.tustar.demo.R

class BookViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_jet_book, parent, false)) {

    private val name = itemView.findViewById<TextView>(R.id.name)
    var book: Book? = null

    fun bindTo(book: Book?) {
        this.book = book
        name.text = book?.name
    }
}