package com.tustar.compiler


data class Group(val id: Int,
                 val name: Int)

data class Demo(val name: Int,
                var groupId: Int = 0,
                var parentId: Int = -1,
                var actionId: Int = -1)