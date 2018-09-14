package com.tustar.demo.module.jet.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.tustar.demo.R

class EndFragment : Fragment() {

    companion object {
        fun newInstance() = EndFragment()
    }

    private lateinit var viewModel: EndViewModel
    private lateinit var content: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_jet_end, container, false)
        with(view) {
            content = findViewById(R.id.jet_text)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EndViewModel::class.java)

        viewModel.data.observe(this, Observer { data ->
            content.text = data
        })
    }
}
