package com.tustar.demo.ui.optimize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentChatsBinding
import com.tustar.demo.ex.bind
import com.tustar.demo.util.Logger


class ChatsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onResume() {
        Logger.i()
        super.onResume()
    }

    override fun onPause() {
        Logger.i()
        super.onPause()
    }

    companion object {
        fun newInstance() = ChatsFragment()
    }
}