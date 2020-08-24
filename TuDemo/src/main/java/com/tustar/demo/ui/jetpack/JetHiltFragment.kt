package com.tustar.demo.ui.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tustar.annotation.GROUP_JETPACK_ID
import com.tustar.annotation.RowDemo
import com.tustar.annotation.RowGroup
import com.tustar.demo.R

@RowGroup(id = GROUP_JETPACK_ID, name = R.string.group_jetpack )
@RowDemo(groupId = GROUP_JETPACK_ID, name = R.string.jet_hilt,
    actionId = R.id.action_home_to_jet_hilt)
class JetHiltFragment : Fragment() {

    private val viewModel: JetHiltViewModel by viewModels {
        JetHiltViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jet_hilt, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance() = JetHiltFragment()
    }
}