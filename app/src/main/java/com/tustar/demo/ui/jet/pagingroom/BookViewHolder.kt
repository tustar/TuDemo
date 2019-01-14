package com.tustar.demo.ui.jet.pagingroom

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.tustar.demo.R

class BookViewHolder(parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_jet_book, parent, false)) {

    private val name = itemView.findViewById<TextView>(R.id.name)
    var book: Book? = null

    fun bindTo(book: Book?) {
        this.book = book
        name.text = book?.name
    }
}