// IOnNewBookArrivedListener.aidl
package com.tustar.demo.module.ryg.ch2.aidl;

import com.tustar.demo.module.ryg.ch2.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
