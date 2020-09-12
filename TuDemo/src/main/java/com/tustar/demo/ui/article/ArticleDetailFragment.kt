package com.tustar.demo.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.tustar.demo.databinding.FragmentArticleDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private val viewModel by viewModels<ArticleViewModel>()

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
        binding.articleDetailWeb.apply {
            loadUrl(args.article.url)
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    if (newProgress == 100) {
                        binding.articleDetailProgress.visibility = View.GONE
                    } else {
                        binding.articleDetailProgress.visibility = View.VISIBLE
                        binding.articleDetailProgress.progress = progress
                    }
                    super.onProgressChanged(view, newProgress)
                }
            }
            settings.apply {
                javaScriptEnabled = true
            }
        }
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        with(viewModel) {
        }
    }
}