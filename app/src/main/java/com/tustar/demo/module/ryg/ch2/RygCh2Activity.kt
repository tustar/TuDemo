package com.tustar.demo.module.ryg.ch2

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.module.ryg.ch2.aidl.BookManagerActivity
import com.tustar.demo.module.ryg.ch2.binderpool.BinderPoolActivity
import com.tustar.demo.module.ryg.ch2.messenger.MessengerActivity
import com.tustar.demo.module.ryg.ch2.provider.RygProviderActivity
import com.tustar.demo.module.ryg.ch2.socket.TCPClientActivity
import com.tustar.demo.module.ryg.ch5.BaseRygActivity

class RygCh2Activity : BaseRygActivity(), SimpleListItem1Adapter.OnItemClickListener {

    init {
        sClassList.add(RygOneActivity::class.java)
        sClassList.add(RygProviderActivity::class.java)
        sClassList.add(MessengerActivity::class.java)
        sClassList.add(BookManagerActivity::class.java)
        sClassList.add(TCPClientActivity::class.java)
        sClassList.add(BinderPoolActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ryg_data_source = R.array.ryg_ch2_list
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch2_title)
    }
}
