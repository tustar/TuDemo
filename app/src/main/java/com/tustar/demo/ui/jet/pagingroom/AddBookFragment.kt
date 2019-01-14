package com.tustar.demo.ui.jet.pagingroom

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import com.tustar.demo.R

class AddBookFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = AddBookFragment()
    }

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(BookViewModel::class.java)
    }
    private lateinit var name: EditText
    private lateinit var addBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_jet_add_book, container, false)
        with(view) {
            name = findViewById(R.id.jet_name)
            name.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addBook()
                    return@setOnEditorActionListener true
                }
                false
            }
            name.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    addBook()
                    return@setOnKeyListener true
                }
                false
            }
            addBtn = findViewById(R.id.jet_add_btn)
            addBtn.setOnClickListener {
                addBook()
            }
        }
        return view
    }

    private fun addBook() {
        val newBook = name.text.trim()
        if (newBook.isNotEmpty()) {
            viewModel.insert(newBook)
            name.setText("")
            fragmentManager?.popBackStack()
        }
    }
}
