package com.tustar.demo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentHomeBinding
import com.tustar.demo.ex.bind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private val binding: FragmentHomeBinding by bind()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainAdapter = HomeAdapter()
        binding.mainList.adapter = mainAdapter
        addLiveObserver(mainAdapter)

        //
        viewModel.findDemos()
    }

    private fun addLiveObserver(mainAdapter: HomeAdapter) {
        with(viewModel) {
            demos.observe(viewLifecycleOwner, Observer { items ->
                mainAdapter.submitList(items)
            })
        }
    }
}
