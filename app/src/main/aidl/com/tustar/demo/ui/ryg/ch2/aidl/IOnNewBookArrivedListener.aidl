// IOnNewBookArrivedListener.aidl
package com.tustar.demo.ui.ryg.ch2.aidl;

import com.tustar.demo.ui.ryg.ch2.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
