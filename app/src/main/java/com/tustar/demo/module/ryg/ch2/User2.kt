package com.tustar.demo.module.ryg.ch2

import android.os.Parcel
import android.os.Parcelable


/**
 * Created by tustar on 17-6-15.
 */

class User2(var userId: Int, var userName: String, var isMale: Boolean, var book: Book) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<User2> = object : Parcelable.Creator<User2> {
            override fun createFromParcel(source: Parcel): User2 = User2(source)
            override fun newArray(size: Int): Array<User2?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            1 == source.readInt(),
            source.readParcelable<Book>(Book::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(userId)
        dest.writeString(userName)
        dest.writeInt((if (isMale) 1 else 0))
        dest.writeParcelable(book, 0)
    }

    override fun toString(): String {
        return "User2(userId=$userId, userName='$userName', isMale=$isMale, book=$book)"
    }
}

