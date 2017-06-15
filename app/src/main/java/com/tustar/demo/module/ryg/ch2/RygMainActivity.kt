package com.tustar.demo.module.ryg.ch2

import android.content.Context
import com.tustar.demo.util.Logger
import kotlinx.android.synthetic.main.activity_ryg_main.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class RygMainActivity : com.tustar.demo.base.BaseActivity() {

    companion object {
        private val TAG = RygMainActivity::class.java.simpleName
        private val FILE_NAME_1 = "cache1.txt"
        private val FILE_NAME_2 = "cache2.txt"
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.tustar.demo.R.layout.activity_ryg_main)

        ryg_second_btn.setOnClickListener {
            var intent = android.content.Intent()
            intent.setClass(this@RygMainActivity, RygSecondActivity::class.java)
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
}
