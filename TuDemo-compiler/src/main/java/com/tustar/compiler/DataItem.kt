package com.tustar.compiler


data class Group(val id: Int,
                 val name: Int)

data class Demo(var groupId: Int = 0,
                val name: Int,
                var actionId: Int,
                var parentId: Int = -1)