package com.tustar.demo.module.jet.pagingroom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(@PrimaryKey(autoGenerate = true) val id: Int, val name: String)
