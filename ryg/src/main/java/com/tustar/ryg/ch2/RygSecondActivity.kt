package com.tustar.ryg.ch2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tustar.ryg.R
import com.tustar.ryg.ch2.manager.UserManager
import com.tustar.ryg.ch2.utils.MyConstants
import com.tustar.util.Logger
import kotlinx.android.synthetic.main.activity_ryg_second.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.ObjectInputStream

class RygSecondActivity : AppCompatActivity() {

    companion object {
        private val TAG = RygSecondActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate :: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_second)
        title = getString(R.string.ryg_ch2_second)

        ryg_third_btn.setOnClickListener {
            startActivity(Intent(this@RygSecondActivity, RygThirdActivity::class.java))
        }

        Logger.d(TAG, "UserManager.sUserId = " + UserManager.sUserId)
    }

    override fun onResume() {
        super.onResume()

        recoverFromFile()
    }

    private fun recoverFromFile() {
        Thread(Runnable {
            var cachedFile = File(MyConstants.CACHE_FILE_PATH)
            if (cachedFile.exists()) {
                try {
                    ObjectInputStream(FileInputStream(cachedFile)).use { ois ->
                        var user = ois.readObject()
                        Logger.d(TAG, "recoverFromFile :: user = " + user)
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }).start()
    }
}
