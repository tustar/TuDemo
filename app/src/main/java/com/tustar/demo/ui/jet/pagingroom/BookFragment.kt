package com.tustar.demo.ui.jet.pagingroom

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import com.tustar.demo.R

class BookFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = BookFragment()
    }

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(BookViewModel::class.java)
    }
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_jet_book, container, false)
        with(view) {
            recyclerView = findViewById(R.id.jet_recycler_view)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = BookAdapter()
        recyclerView.adapter = adapter

        viewModel.allBooks.observe(this, Observer(adapter::submitList))
        initSwipeToDelete()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.fragment_jet_book, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                view?.let {
                    Navigation.findNavController(it).navigate(R.id.action_add_book)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: androidx.recyclerview.widget.RecyclerView,
                                          viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0, ItemTouchHelper.LEFT
                            or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                                target: androidx.recyclerview.widget.RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as BookViewHolder)?.book?.let {
                    viewModel.remove(it)
                }
            }
        }).attachToRecyclerView(recyclerView)
    }
}
