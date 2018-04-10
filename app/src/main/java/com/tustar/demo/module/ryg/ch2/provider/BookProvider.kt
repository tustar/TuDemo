package com.tustar.demo.module.ryg.ch2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.tustar.common.util.Logger

/**
 * Created by tustar on 17-6-26.
 */
class BookProvider : ContentProvider() {

    companion object {
        private val TAG = BookProvider::class.simpleName
        val AUTHORITY = "com.tustar.demo.module.ryg.ch2.provider"
        val BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book")
        val USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user")
        val BOOK_URI_CODE = 0
        val USER_URI_CODE = 1
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE)
            sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE)
        }
    }

    private var mContext: Context? = null
    private var mWdb: SQLiteDatabase? = null
    private var mRdb: SQLiteDatabase? = null

    override fun onCreate(): Boolean {
        Logger.d(TAG, "onCreate :: current thread:" + Thread.currentThread().name)
        mContext = context
        var helper = DbOpenHelper(mContext)
        mWdb = helper.writableDatabase
        mRdb = helper.readableDatabase
        mWdb!!.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME)
        mWdb!!.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME)
        initData()
        return true
    }

    private fun initData() {
        mWdb!!.execSQL("insert into book values(3,'Android');")
        mWdb!!.execSQL("insert into book values(4,'Ios');")
        mWdb!!.execSQL("insert into book values(5,'Html5');")
        mWdb!!.execSQL("insert into user values(1,'jake',1);")
        mWdb!!.execSQL("insert into user values(2,'jasmine',0);")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Logger.d(TAG, "insert ::")
        var table = getTableName(uri) ?: throw IllegalArgumentException("Unsupported URI: " + uri)
        mWdb!!.insert(table, null, values)
        mContext!!.contentResolver.notifyChange(uri, null)
        return uri
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?,
                       selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        Logger.d(TAG, "query :: current thread:" + Thread.currentThread().name)
        var table = getTableName(uri) ?: throw IllegalArgumentException("Unsupported URI: " + uri)
        return mRdb!!.query(table, projection, selection, selectionArgs, null, null, sortOrder, null)
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<out String>?): Int {
        Logger.d(TAG, "update ::")
        var table = getTableName(uri) ?: throw IllegalArgumentException("Unsupported URI: " + uri)
        var count = mWdb!!.update(table, values, selection, selectionArgs)
        if (count > 0) {
            mContext!!.contentResolver.notifyChange(uri, null)
        }
        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Logger.d(TAG, "delete ::")
        var table = getTableName(uri) ?: throw IllegalArgumentException("Unsupported URI: " + uri)
        var count = mWdb!!.delete(table, selection, selectionArgs)
        if (count > 0) {
            mContext!!.contentResolver.notifyChange(uri, null)
        }
        return count
    }

    override fun getType(uri: Uri): String? {
        Logger.d(TAG, "getType ::")
        return null
    }

    private fun getTableName(uri: Uri): String? {
        var tableName: String? = null
        when (sUriMatcher.match(uri)) {
            BOOK_URI_CODE -> tableName = DbOpenHelper.BOOK_TABLE_NAME
            USER_URI_CODE -> tableName = DbOpenHelper.USER_TABLE_NAME
            else -> {
            }
        }

        return tableName
    }
}