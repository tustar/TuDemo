package com.tustar.demo.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.tustar.demo.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private val viewModel: ArticleViewModel by viewModels {
        ArticleViewModelFactory(requireContext())
    }
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentArticleBinding.inflate(
            inflater, container,
            false
        )
        articleAdapter = ArticleAdapter()
        binding.articleRecyclerView.apply {
            adapter = articleAdapter
            val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
        }
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        with(viewModel) {
            articles.observe(viewLifecycleOwner, Observer { items ->
                articleAdapter.submitList(items)
            })
        }
    }
}