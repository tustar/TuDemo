package com.tustar.ryg.ch5

import android.os.Bundle
import android.widget.Toast
import com.tustar.ryg.R
import androidx.appcompat.app.AppCompatActivity

class RygCh5RemoteViewsActivity : AppCompatActivity() {

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
