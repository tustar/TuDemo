package com.tustar.demo.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentTodoBinding
import com.tustar.demo.ex.bind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoFragment : Fragment() {

    private val viewModel:TodoViewModel by viewModels()
    private val binding: FragmentTodoBinding by bind()

    private val todoAdapter by lazy {
        TodoAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.todoRecyclerView.apply {
            adapter = todoAdapter
            val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
        }
        addLiveObserver()
    }

    private fun addLiveObserver() {
        with(viewModel) {
            todos.observe(viewLifecycleOwner, Observer { items ->
                todoAdapter.submitList(items)
            })
        }
    }
}