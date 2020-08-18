package com.tustar.demo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tustar.demo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container,
                false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        context ?: return binding.root

        val mainAdapter = HomeAdapter()
        binding.mainList.adapter = mainAdapter
        subscribeUi(mainAdapter)

        //
        viewModel.findDemos()

        return binding.root
    }

    private fun subscribeUi(mainAdapter: HomeAdapter) {
        with(viewModel) {
            demos.observe(viewLifecycleOwner, Observer {
                    items ->
                mainAdapter.submitList(items)
            })
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
