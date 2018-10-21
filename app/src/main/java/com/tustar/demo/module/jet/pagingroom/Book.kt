package com.tustar.demo.module.jet.pagingroom

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Book(@PrimaryKey(autoGenerate = true) val id: Int, val name: String)
