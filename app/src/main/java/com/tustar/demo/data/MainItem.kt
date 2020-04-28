package com.tustar.demo.data

import androidx.room.*
import java.util.*

sealed class MainItem {
    abstract fun getType(): Int

    companion object {
        const val TYPE_GROUP = 0
        const val TYPE_CHILD = 1
    }
}

@Entity(tableName = "groups")
data class Group(@PrimaryKey
                 val id: Long,
                 val name: Int,
                 @ColumnInfo(name = "created_at")
                 val createdAt: Calendar = Calendar.getInstance()) : MainItem() {
    override fun getType(): Int {
        return TYPE_GROUP
    }
}


@Entity(tableName = "demos",
        foreignKeys = [
            ForeignKey(entity = Group::class, parentColumns = ["id"],
                    childColumns = ["group_id"])],
        indices = [Index("group_id")])
data class Demo(@PrimaryKey(autoGenerate = true)
                var id: Long = 0,
                val name: Int,
                @ColumnInfo(name = "group_id")
                var groupId: Long = 0,
                @ColumnInfo(name = "parent_id")
                var parentId: Long = -1,
                @ColumnInfo(name = "action_id")
                var actionId: Int,
                @ColumnInfo(name = "is_menu")
                val isMenu: Boolean = false,
                @ColumnInfo(name = "created_at")
                val createdAt: Calendar = Calendar.getInstance()) : MainItem() {

    override fun getType(): Int {
        return TYPE_CHILD
    }
}