package com.tustar.demo.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentArticleBinding
import com.tustar.demo.ex.bind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private val viewModel: ArticleViewModel by viewModels()
    private val binding: FragmentArticleBinding by bind()

    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleAdapter = ArticleAdapter().apply {
            onItemClick = {
                val action = ArticleFragmentDirections.actionArticleToDetail(it)
                findNavController().navigate(action)
            }
        }
        binding.articleRecyclerView.apply {
            adapter = articleAdapter
            val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
        }
        addLiveObserver()
    }

    private fun addLiveObserver() {
        with(viewModel) {
            articles.observe(viewLifecycleOwner, Observer { items ->
                articleAdapter.submitList(items)
            })
        }
    }
}