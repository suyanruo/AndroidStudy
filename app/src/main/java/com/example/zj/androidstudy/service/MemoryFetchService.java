package com.example.zj.androidstudy.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import com.example.zj.androidstudy.IMemoryAidlInterface;

import java.io.FileDescriptor;
import java.lang.reflect.Method;

import androidx.annotation.Nullable;

public class MemoryFetchService extends Service {
  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return new MemoryFetchStub();
  }

  static class MemoryFetchStub extends IMemoryAidlInterface.Stub {
    @Override
    public ParcelFileDescriptor getParcelFileDescriptor() throws RemoteException {
      MemoryFile memoryFile = null;
      try {
        memoryFile = new MemoryFile("test_memory", 1024);
        memoryFile.getOutputStream().write(new byte[]{1, 2, 3, 4, 5});
        Method method = MemoryFile.class.getDeclaredMethod("getFileDescriptor");
        FileDescriptor des = (FileDescriptor) method.invoke(memoryFile);
        return ParcelFileDescriptor.dup(des);
      } catch (Exception e) {}
      return null;
    }
  }
}
