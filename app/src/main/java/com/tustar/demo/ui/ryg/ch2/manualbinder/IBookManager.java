package com.tustar.demo.ui.ryg.ch2.manualbinder;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.tustar.demo.ui.ryg.ch2.model.Book;

import java.util.List;

/**
 * Created by tustar on 17-6-26.
 */

public interface IBookManager extends IInterface {

    String DESCRIPTOR = "com.tustar.demo.ui.ryg.ch2.manualbinder.IBookManager";

    int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;
    int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    List<Book> getBookList() throws RemoteException;

    void addBook(Book book) throws RemoteException;
}
