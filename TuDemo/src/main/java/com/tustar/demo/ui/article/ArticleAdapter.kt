package com.tustar.demo.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tustar.demo.data.Article
import com.tustar.demo.databinding.ItemArticleBinding
import java.text.SimpleDateFormat
import java.util.*


class ArticleAdapter() : ListAdapter<Article, RecyclerView.ViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArticleViewHolder(ItemArticleBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            val item = getItem(position)
            if (item is Article) {
                holder.bind(item)
            }
        }
    }


    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {

            }
        }

        fun bind(item: Article) {
            binding.apply {
                articleTitle.text = item.title
                articleDescription.text = item.description
                articleAuthor.text = item.author
                articleCreateAt.text = SimpleDateFormat("yyyy-MM-dd")
                    .format(Date(item.createAt))
            }
        }
    }

    private class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}