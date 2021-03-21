package com.example.zj.androidstudy.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zj.aidl.Book;
import com.example.zj.aidl.IBookManager;
import com.example.zj.aidl.IOnNewBookArrivedListener;
import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.tool.FileUtil;

import java.util.List;

public class BinderActivity extends AppCompatActivity {
    private static final String BOOK_FILE_PATH = "Book";

    private TextView tvCurrentBook;
    private TextView tvSavedBook;
    private Button btnAddBook;
    private Button btnSaveBook;
    private Button btnReadBook;

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            Book book = (Book) msg.obj;
            Toast.makeText(BinderActivity.this,
                "new book arrive: " + book.name +
                    "  Author: " + book.authorInfo.name, Toast.LENGTH_SHORT).show();
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
            // 运行在客户端Binder线程池
            // binder死亡时，重新绑定远程服务的流程
            if (mRemoteBookManager == null) {
                return;
            }
            mRemoteBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mRemoteBookManager = null;
            // 重新绑定远程服务方法一
            bindBinderService();
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 运行在UI线程
            IBookManager iBookManager = IBookManager.Stub.asInterface(service);
            try {
                // 添加死亡代理
                service.linkToDeath(mDeathRecipient, 0);

                mRemoteBookManager = iBookManager;
                mRemoteBookManager.registerListener(mOnNewBookArrivedListener);
                // 为了防止耗时操作导致ANR，建议执行服务端耗时方法时放在子线程中执行
                List<Book> books = iBookManager.getBookList();
                printBook(books, tvCurrentBook);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 运行在UI线程
            mRemoteBookManager = null;
            // 重新绑定远程服务方法二
            // bindBinderService();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);

        tvCurrentBook = findViewById(R.id.tv_current_book);
        tvSavedBook = findViewById(R.id.tv_saved_book);
        btnAddBook = findViewById(R.id.btn_add_book);
        btnSaveBook = findViewById(R.id.btn_save_book);
        btnReadBook = findViewById(R.id.btn_read_book);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mRemoteBookManager.addBook(new Book("android", 100));
                    List<Book> newBooks = mRemoteBookManager.getBookList();
                    printBook(newBooks, tvCurrentBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        btnSaveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveBookInfo(mRemoteBookManager.getBookList());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        btnReadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printBook(readBookInfo(), tvSavedBook);
            }
        });

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

    private void printBook(List<Book> bookList, TextView tvContent) {
        if (bookList == null || bookList.size() == 0) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("Book List\n");
        for (Book book : bookList) {
            sb.append(book.name + " : " + book.price).append("   ");
        }
        tvContent.setText(sb);
    }

    private void saveBookInfo(List<Book> bookList) {
        FileUtil.persistToFile(this, bookList, BOOK_FILE_PATH );
    }

    private List<Book> readBookInfo() {
        List<Book> bookList = (List<Book>) FileUtil.recoverFromFile(this, BOOK_FILE_PATH);
        return bookList;
    }
}
