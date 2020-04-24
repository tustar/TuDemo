package com.tustar.demo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tustar.demo.databinding.FragmentMainBinding
import com.tustar.ex.observe
import com.tustar.widget.Decoration

class MainFragment : Fragment(), MainAdapter.OnItemClickListener {

    private lateinit var mainAdapter: MainAdapter
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainAdapter = MainAdapter(emptyList(), this@MainFragment)
        with(binding.mainContainer) {
            addItemDecoration(Decoration(requireContext()))
            adapter = mainAdapter
        }

        with(viewModel) {
            observe(hencoders) {
                mainAdapter.items = it
                mainAdapter.notifyDataSetChanged()
            }
        }
        viewModel.addHencoderDemos()
    }

    override fun onItemClick(item: ChildItem) {

    }

    companion object {
        fun newInstance() = MainFragment()
    }

}
