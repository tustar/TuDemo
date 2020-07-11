// IOnNewBookArrivedListener.aidl
package com.tustar.ryg.ch2.aidl;

import com.tustar.ryg.ch2.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
