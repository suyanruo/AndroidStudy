package com.example.zj.androidstudy.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.example.zj.aidl.Book;
import com.example.zj.aidl.IBookManager;
import com.example.zj.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private AtomicBoolean mIsDestoryed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    /**
     * 定义监听者列表
     * 方式一：使用CopyOnWriteArrayList，接触监听时存在问题
     * 方式二：使用RemoteCallbackList，利用binder映射listener，保证在解除监听者时的正确性
     */
//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mRemoteListenerList = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!mListenerList.contains(listener)) {
//                mListenerList.add(listener);
//            }
            mRemoteListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (mListenerList.contains(listener)) {
//                mListenerList.remove(listener);
//            }
            mRemoteListenerList.unregister(listener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book("安徒生童話", 80));
        mBookList.add(new Book("伊索寓言", 54));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsDestoryed.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        final int size = mRemoteListenerList.beginBroadcast();
        for (int i = 0; i < size; i++) {
            IOnNewBookArrivedListener listener = mRemoteListenerList.getBroadcastItem(i);
            if (listener != null) {
                listener.onNewBookArrived(book);
            }
        }
        mRemoteListenerList.finishBroadcast();

//        for (IOnNewBookArrivedListener listener : mListenerList) {
//            listener.onNewBookArrived(book);
//        }
    }

    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            while (!mIsDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    onNewBookArrived(new Book("new book#" + (mBookList.size() + 1), 20));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
