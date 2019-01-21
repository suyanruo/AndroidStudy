package com.example.zj.androidstudy.tool;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentUtil {

    public static void setRootFragment(AppCompatActivity context, int resId, Fragment rootF) {
        FragmentManager manager = context.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(resId, rootF, rootF.getClass().getName());
        transaction.commitAllowingStateLoss();
    }

    public static void enterNewFragment(AppCompatActivity context, int resId, Fragment currentF, Fragment newF) {
        FragmentManager manager = context.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(currentF);
        transaction.add(resId, newF, newF.getClass().getName());
        transaction.commitAllowingStateLoss();
    }
}
