package com.tustar.demo.module.ryg.ch2

import java.io.Serializable

/**
 * Created by tustar on 17-6-15.
 */

class User(var userId: Int, var userName: String, var isMale: Boolean) : Serializable {
    override fun toString(): String {
        return "User(userId=$userId, userName='$userName', isMale=$isMale)"
    }
}
