// IBookManager.aidl
package com.tustar.demo.module.ryg.ch2.aidl;

import com.tustar.demo.module.ryg.ch2.aidl.Book;
import com.tustar.demo.module.ryg.ch2.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener( IOnNewBookArrivedListener listener);
    void unregisterListener( IOnNewBookArrivedListener listener);
}
