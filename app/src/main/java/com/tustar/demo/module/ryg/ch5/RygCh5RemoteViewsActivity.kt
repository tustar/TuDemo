package com.tustar.demo.module.ryg.ch5

import android.os.Bundle
import android.widget.Toast
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class RygCh5RemoteViewsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch5_remote_views)
        title = getString(R.string.ryg_ch5_remote_views)

        if (intent != null) {
            Toast.makeText(this, "sid = " + intent.getStringExtra("sid"),
                    Toast.LENGTH_SHORT).show()
        }
    }
}
