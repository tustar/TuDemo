package com.tustar.ushare.ui.mine

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tustar.ushare.R


class MineFragment : Fragment(), MineContract.View {

    override lateinit var presenter: MineContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ushare_fragment_mine, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = MinePresenter(this)
    }

    override fun onDetach() {
        super.onDetach()
        presenter?.detachView()
    }


    companion object {
        @JvmStatic
        fun newInstance() =
                MineFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
