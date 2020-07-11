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
import com.tustar.widget.Decoration

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater, container,
                false).apply {
            lifecycleOwner = viewLifecycleOwner
            mainList.addItemDecoration(Decoration(requireContext()))
        }
        context ?: return binding.root

        val mainAdapter = MainAdapter()
        val mergeAdapter = MergeAdapter(mainAdapter)
        binding.mainList.adapter = mergeAdapter
        subscribeUi(mainAdapter)

        //
        viewModel.findDemos()

        return binding.root
    }

    private fun subscribeUi(mainAdapter: MainAdapter) {
        with(viewModel) {
            observe(demos) { items ->
                mainAdapter.submitList(items)
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
