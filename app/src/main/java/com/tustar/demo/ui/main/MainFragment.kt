package com.tustar.demo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.MergeAdapter
import com.tustar.demo.databinding.FragmentMainBinding
import com.tustar.ex.observe
import com.tustar.util.Logger
import com.tustar.widget.Decoration

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater, container,
                false).apply {
            lifecycleOwner = viewLifecycleOwner
            mainList.addItemDecoration(Decoration(requireContext()))
        }
        context ?: return binding.root

        val hencoderAdapter = MainAdapter()
        val booksAdapter = MainAdapter()
        val mergeAdapter = MergeAdapter(hencoderAdapter, booksAdapter)
        binding.mainList.adapter = mergeAdapter
        with(viewModel) {
            observe(hencoderDemos) { items ->
                hencoderAdapter.submitList(items)
            }
            observe(bookDemos) { items ->
                booksAdapter.submitList(items)
            }
        }

        return binding.root
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
