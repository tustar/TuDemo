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
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentArticleDetailBinding
import com.tustar.demo.ex.bind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private val viewModel: ArticleViewModel by viewModels()
    private val binding: FragmentArticleDetailBinding by bind()

    val args: ArticleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        addLiveObserver()
    }

    private fun addLiveObserver() {
        with(viewModel) {
        }
    }
}