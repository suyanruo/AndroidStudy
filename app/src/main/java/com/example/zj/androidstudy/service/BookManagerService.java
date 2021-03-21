package com.example.zj.androidstudy.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.example.zj.aidl.Book;
import com.example.zj.aidl.IBookManager;
import com.example.zj.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private AtomicBoolean mIsDestroyed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    /**
     * 定义监听者列表
     * 方式一：使用CopyOnWriteArrayList，解除监听时存在问题
     * 方式二：使用RemoteCallbackList，利用binder映射listener，保证在解除监听者时的正确性
     */
//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mRemoteListenerList = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            synchronized (mBookList) {
                return mBookList;
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (mBookList) {
                if (!mBookList.contains(book)) {
                    mBookList.add(book);
                }
            }
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

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            // 权限验证渠道二：覆写onTransact方法。这种方式如果验证不通过只是执行一个空的远程方法，而不会终止执行AIDL中的方法
            // 验证方法一：自定义权限
            int check = checkCallingOrSelfPermission("com.example.zj.androidstudy.permission.ACCESS_BOOK_SERVICE");
            if (check == PackageManager.PERMISSION_DENIED) {
              return false;
            }
            // 验证方法二：通过Uid来获取包名，通过限制指定包名来验证身份
            String packageName = "";
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            if (!packageName.startsWith("com.example.zj.androidstudy")) {
                return false;
            }

            return super.onTransact(code, data, reply, flags);
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
      // 权限验证渠道一：在onBind方法中验证权限
      int check = checkCallingOrSelfPermission("com.example.zj.androidstudy.permission.ACCESS_BOOK_SERVICE");
      if (check == PackageManager.PERMISSION_GRANTED) {
        return mBinder;
      }
      return null;
    }

    @Override
    public void onDestroy() {
        mIsDestroyed.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        final int size = mRemoteListenerList.beginBroadcast();
        for (int i = 0; i < size; i++) {
            IOnNewBookArrivedListener listener = mRemoteListenerList.getBroadcastItem(i);
            if (listener != null) {
                // 此回调会运行在客服端的binder线程池中，不能在UI线程中调用此回调
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
            while (!mIsDestroyed.get()) {
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
