package com.example.zj.androidstudy.tool;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtil {

    public static void setRootFragment(FragmentActivity context, int resId, Fragment rootF) {
        FragmentManager manager = context.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(resId, rootF, rootF.getClass().getName());
        transaction.commitAllowingStateLoss();
    }

    public static void enterNewFragment(FragmentActivity context, int resId, Fragment currentF, Fragment newF) {
        FragmentManager manager = context.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(currentF);
        transaction.add(resId, newF);
        transaction.commitAllowingStateLoss();
    }
}
