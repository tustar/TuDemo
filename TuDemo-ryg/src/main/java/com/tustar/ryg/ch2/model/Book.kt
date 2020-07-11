package com.tustar.ryg.ch2.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by tustar on 17-6-15.
 */
class Book : Parcelable {

    var id: Int = 0
    var name: String? = null

    constructor()

    constructor(id: Int, name: String) : this() {
        this.id = id
        this.name = name
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Book> = object : Parcelable.Creator<Book> {
            override fun createFromParcel(source: Parcel): Book = Book(source)
            override fun newArray(size: Int): Array<Book?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
    }

    override fun toString(): String {
        return "Book(id=$id, name='$name')"
    }
}