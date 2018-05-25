package com.tustar.ushare.ui.lot

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tustar.common.util.Logger
import com.tustar.ushare.R
import com.tustar.ushare.data.bean.User
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class LotFragment : Fragment(), LotContract.View, LotAdapter.OnItemClickListener {

    override lateinit var presenter: LotContract.Presenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LotAdapter
    private var users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.ushare_fragment_lot, container,
                false)
        initRecycleView(view)
        activity?.let {
            presenter.getUsers(it)
        }

        return view
    }

    private fun initRecycleView(view: View) {
        recyclerView = view.find(R.id.lot_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = LotAdapter(users)
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(view: View, position: Int) {
        // TODO
    }

    override fun showToast(resId: Int) {
        toast(resId)
    }

    override fun updateUsers(users: MutableList<User>) {
        Logger.d("users = $users")
        this.users.clear()
        this.users.addAll(users)
        adapter.users = users
        adapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = LotPresenter(this)
    }

    override fun onDetach() {
        super.onDetach()
        presenter?.detachView()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                LotFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
