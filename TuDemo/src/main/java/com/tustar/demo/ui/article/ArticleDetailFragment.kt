package com.tustar.demo.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.tustar.demo.databinding.FragmentArticleDetailBinding


class ArticleDetailFragment : Fragment() {

    private val viewModel: ArticleViewModel by viewModels {
        ArticleViewModelFactory(requireContext())
    }
    private lateinit var binding: FragmentArticleDetailBinding
    val args: ArticleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleDetailBinding.inflate(
            inflater, container,
            false
        )
        binding.articleDetailWeb.loadUrl(args.article.url)
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        with(viewModel) {
        }
    }
}