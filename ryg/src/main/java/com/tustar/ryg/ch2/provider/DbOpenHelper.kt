package com.tustar.ryg.ch2.provider

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by tustar on 17-6-26.
 */
class DbOpenHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?,
                   version: Int, errorHandler: DatabaseErrorHandler?) :
        SQLiteOpenHelper(context, name, factory, version, errorHandler) {

    constructor(context: Context?) : this(context, DB_NAME, null, DB_VERSION, null)

    companion object {
        private val DB_NAME = "book_provider.db"
        private val DB_VERSION = 1

        val BOOK_TABLE_NAME = "book"
        val USER_TABLE_NAME = "user"

        private val CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS " +
                BOOK_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT)"

        private val CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " +
                USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT," + "sex INT)"
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_BOOK_TABLE)
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}