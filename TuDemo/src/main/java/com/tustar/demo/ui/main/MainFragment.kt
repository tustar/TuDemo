package com.tustar.demo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tustar.demo.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater, container,
                false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        context ?: return binding.root

        val mainAdapter = MainAdapter()
        binding.mainList.adapter = mainAdapter
        subscribeUi(mainAdapter)

        //
        viewModel.findDemos()

        return binding.root
    }

    private fun subscribeUi(mainAdapter: MainAdapter) {
        with(viewModel) {
            demos.observe(viewLifecycleOwner, Observer {
                    items ->
                mainAdapter.submitList(items)
            })
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
