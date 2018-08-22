package com.example.zj.aidl;

import com.example.zj.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
