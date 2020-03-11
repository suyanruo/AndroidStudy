package com.example.zj.androidstudy.rxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 2020-01-14.
 */
public class RxjavaDemo {
  private static final String TAG = "RxjavaDemo";

  public static void testCreate() {
    Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter emitter) throws Exception {
        emitter.onNext(1);
      }
    });
    observable.subscribe(new Observer<Integer>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(Integer i) {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  public static void testFromArray() {
    Integer[] array = {1, 2, 3, 4};
    Observable.fromArray(array)
        .subscribe(new Observer<Integer>() {
      @Override
      public void onSubscribe(Disposable d) {
        Log.d(TAG, "=================onSubscribe");
      }

      @Override
      public void onNext(Integer integer) {
        Log.d(TAG, "=================onNext" + integer);
      }

      @Override
      public void onError(Throwable e) {
        Log.d(TAG, "=================onError");
      }

      @Override
      public void onComplete() {
        Log.d(TAG, "=================onComplete");
      }
    });
  }

  public static void testFromIterable() {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      list.add(i);
    }
    Observable.fromIterable(list).subscribe(new Observer<Integer>() {
      @Override
      public void onSubscribe(Disposable d) {
        Log.d(TAG, "=================onSubscribe ");
      }

      @Override
      public void onNext(Integer integer) {
        Log.d(TAG, "=================onNext " + integer);
      }

      @Override
      public void onError(Throwable e) {
      }

      @Override
      public void onComplete() {
        Log.d(TAG, "=================onComplete ");
      }
    });
  }

  public static void testFromCallable() {
    Observable.fromCallable(new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        return 0;
      }
    }).subscribe(new Consumer<Integer>() {
          @Override
          public void accept(Integer integer) throws Exception {
            Log.d(TAG, "================accept " + integer);
          }
        });
  }

  public static void testFromFuture() {
    final FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
      @Override
      public String call() throws Exception {
        return "result";
      }
    });
    Observable.fromFuture(futureTask).doOnSubscribe(new Consumer<Disposable>() {
      @Override
      public void accept(Disposable disposable) throws Exception {
        futureTask.run();
      }
    }).subscribe(new Consumer<String>() {
      @Override
      public void accept(String s) throws Exception {
        Log.d(TAG, "================accept " + s);
      }
    });
  }

  private static Integer i = 1;

  public static void testDefer() {
    Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
      @Override
      public ObservableSource<? extends Integer> call() throws Exception {
        return Observable.just(i);
      }
    });

    i = 200;
    Observer observer = new Observer<Integer>() {

      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(Integer integer) {
        Log.d(TAG, "================onNext " + integer);
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    };

    observable.subscribe(observer);

    i = 300;

    observable.subscribe(observer);
  }

  public static void testTimer() {
    Observable.timer(2, TimeUnit.SECONDS)
        .subscribe(new Observer<Long>() {
      @Override
      public void onSubscribe(Disposable d) {
      }

      @Override
      public void onNext(Long aLong) {
        Log.d(TAG, "===============onNext " + aLong);
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  public static void testIntervalRange() {
    Observable.intervalRange(12, 6, 0, 1, TimeUnit.SECONDS)
        .subscribe(new Observer<Long>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(Long aLong) {
            Log.d(TAG, "===============onNext " + aLong);
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {

          }
        });
  }

  public static void testRange() {
    Observable.range(1, 5).subscribe(new Observer<Integer>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(Integer integer) {
        Log.d(TAG, "===============onNext " + integer);
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  /*---------------------------------- 转换操作符 ----------------------------------*/

  public static void testMap() {
    Observable.just(1, 2, 3)
        .map(new Function<Integer, String>() {
          @Override
          public String apply(Integer integer) throws Exception {
            return "This is " + integer;
          }
        }).subscribe(new Observer<String>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(String s) {
        Log.e(TAG, "===================onNext " + s);
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  public static void testCreate2() {
    Observable
        .interval(1, TimeUnit.SECONDS)
        .take(15)
        .window(3, TimeUnit.SECONDS)
        .subscribe(new Consumer<Observable<Long>>() {
      @Override
      public void accept(Observable<Long> longObservable) throws Exception {
        Log.e(TAG, "Sub Divide begin...\n");
        longObservable.subscribe(new Consumer<Long>() {
          @Override
          public void accept(Long aLong) throws Exception {
            Log.e(TAG, "Next:" + aLong + "\n");
          }
        });
      }
    });
  }
}
