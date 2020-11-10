package com.tustar.demo.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.tustar.demo.databinding.FragmentTodoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoFragment : Fragment() {

    private val viewModel by viewModels<TodoViewModel>()

    private val todoAdapter by lazy {
        TodoAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTodoBinding.inflate(
            inflater, container,
            false
        )
        binding.todoRecyclerView.apply {
            adapter = todoAdapter
            val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
        }
        addLiveObserver()
        return binding.root
    }

    private fun addLiveObserver() {
        with(viewModel) {
            todos.observe(viewLifecycleOwner, Observer { items ->
                todoAdapter.submitList(items)
            })
        }
    }
}