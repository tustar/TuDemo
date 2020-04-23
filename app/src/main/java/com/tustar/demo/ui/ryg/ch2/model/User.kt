package com.tustar.demo.ui.ryg.ch2.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class User : Parcelable, Serializable {

    var id: Int = 0
    var name: String? = null
    var isMale: Boolean = false
    var book: Book? = null

    constructor()

    constructor(id: Int, name: String, isMale: Boolean) : this() {
        this.id = id
        this.name = name
        this.isMale = isMale
    }

    constructor(id: Int, name: String, isMale: Boolean, book: Book) : this(id, name, isMale) {
        this.book = book
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString()!!,
            1 == source.readInt(),
            source.readParcelable<Book>(Book::class.java.classLoader)!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeInt((if (isMale) 1 else 0))
        dest.writeParcelable(book, 0)
    }

    override fun toString(): String {
        return "User(id=$id, name=$name, isMale=$isMale, book=$book)"
    }


}
