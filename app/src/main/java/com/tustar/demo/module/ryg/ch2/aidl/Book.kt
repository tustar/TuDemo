package com.tustar.demo.module.ryg.ch2.aidl

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by tustar on 17-7-3.
 */

class Book : Parcelable {

    var bookId: Int = 0
    var bookName: String? = null

    constructor(bookId: Int, bookName: String) {
        this.bookId = bookId
        this.bookName = bookName
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Book> = object : Parcelable.Creator<Book> {
            override fun createFromParcel(source: Parcel): Book = Book(source)
            override fun newArray(size: Int): Array<Book?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(bookId)
        dest.writeString(bookName)
    }

    override fun toString(): String {
        return "Book(bookId=$bookId, bookName=$bookName)"
    }


}