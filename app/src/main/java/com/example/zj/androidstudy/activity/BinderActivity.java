package com.example.zj.androidstudy.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zj.aidl.Book;
import com.example.zj.aidl.IBookManager;
import com.example.zj.aidl.IOnNewBookArrivedListener;
import com.example.zj.androidstudy.R;

import java.util.List;

public class BinderActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Book book = (Book) msg.obj;
            Log.e("zj", "new book arrive: " + book.name);
        }
    };
    private IBookManager mRemoteBookManager;
    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            // 当前处于binder线程池环境，属于工作线程，需要使用handler切换到主线程
            mHandler.obtainMessage(1, newBook).sendToTarget();
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            // binder死亡时，重新绑定远程服务的流程
            if (mRemoteBookManager == null) {
                return;
            }
            mRemoteBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mRemoteBookManager = null;
            bindBinderService();
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                // 添加死亡代理
                service.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            IBookManager iBookManager = IBookManager.Stub.asInterface(service);
            try {
                mRemoteBookManager = iBookManager;
                mRemoteBookManager.registerListener(mOnNewBookArrivedListener);
                // 为了防止耗时操作导致ANR，建议执行服务端耗时方法时放在子线程中执行
                List<Book> books = iBookManager.getBookList();
                printBook(books);
                iBookManager.addBook(new Book("android", 100));
                List<Book> newBooks = iBookManager.getBookList();
                printBook(newBooks);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);

        bindBinderService();
    }

    private void bindBinderService() {
        // 考虑到5.0以上系统不能使用隐式调用，跨应用调用service，谷歌推荐使用方法：
        Intent intent = new Intent();
        intent.setAction("com.example.zj.bookManagerService");
        intent.setPackage("com.example.zj.androidstudy");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            try {
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }

    private void printBook(List<Book> bookList) {
        StringBuffer sb = new StringBuffer();
        for (Book book : bookList) {
            sb.append(book.name + " : " + book.price).append("   ");
        }
        Log.e("zj", sb.toString());
    }
}
