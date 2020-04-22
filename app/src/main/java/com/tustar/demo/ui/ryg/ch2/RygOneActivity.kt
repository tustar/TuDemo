package com.tustar.demo.ui.ryg.ch2

import android.content.Context
import android.content.Intent
import com.tustar.util.Logger
import com.tustar.demo.R
import com.tustar.demo.ui.ryg.ch2.manager.UserManager
import com.tustar.demo.ui.ryg.ch2.model.User
import com.tustar.demo.ui.ryg.ch2.utils.MyConstants
import kotlinx.android.synthetic.main.activity_ryg_one.*
import java.io.*


class RygOneActivity : com.tustar.demo.base.BaseActivity() {

    companion object {
        private val TAG = RygOneActivity::class.simpleName
        private val FILE_NAME_1 = "cache1.txt"
        private val FILE_NAME_2 = "cache2.txt"
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_one)
        title = getString(R.string.ryg_ch2_ipc)

        ryg_second_btn.setOnClickListener {
            var intent = Intent()
            intent.setClass(this, RygSecondActivity::class.java)
            startActivity(intent)
        }

        UserManager.sUserId = 2
        Logger.d(TAG, "UserManager.sUserId = " + UserManager.sUserId)

        // 序列化过程
        var user = User(0, "jack", true)
        var output = ObjectOutputStream(openFileOutput(FILE_NAME_1, Context.MODE_PRIVATE))
        output.writeObject(user)
        output.close()
        Logger.d(TAG, "user = " + user)

//        var book = Book(1, "Java")
//        var user2 = User2(0, "Tom", true, book)
//        var output2 = ObjectOutputStream(openFileOutput(FILE_NAME_2, Context.MODE_PRIVATE))
//        output2.writeObject(user2)
//        output2.close()

        // 反序列过程
        var input = ObjectInputStream(openFileInput(FILE_NAME_1))
        var newUser = input.readObject()
        input.close()
        Logger.d(TAG, "newUser = " + newUser)

//        var input2 = ObjectInputStream(openFileInput(FILE_NAME_2))
//        var newUser2 = input2.readObject()
//        input2.close()
//        Logger.d(TAG, "newUser2 = " + newUser2)
    }

    override fun onResume() {
        super.onResume()

        persistToFile()
    }

    private fun persistToFile() {
        Thread(Runnable {
            var user = User(1, "hello world", false)
            var dir = File(MyConstants.CHAPTER_2_PATH)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            var cachedFile = File(MyConstants.CACHE_FILE_PATH)
            try {
                ObjectOutputStream(FileOutputStream(cachedFile)).use { oos ->
                        oos.writeObject(user)
                        Logger.d(TAG, "persistToFile :: user = " + user)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }).start()
    }
}
