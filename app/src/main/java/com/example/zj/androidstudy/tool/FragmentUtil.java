package com.example.zj.androidstudy.tool;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

public class FragmentUtil {

    public static void setRootFragment(FragmentActivity context, int resId, Fragment rootF) {
        FragmentManager manager = context.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(resId, rootF, rootF.getClass().getName());
        transaction.commitAllowingStateLoss();
    }

    public static void enterNewFragment(FragmentActivity context, int resId, Fragment newF) {
        FragmentManager manager = context.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment : fragments) {
            transaction.hide(fragment);
            // 设置当前fragment生命周期上线，实现懒加载
            transaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED);
        }
        transaction.add(resId, newF);
        transaction.setMaxLifecycle(newF, Lifecycle.State.RESUMED);
        transaction.commitAllowingStateLoss();
    }

    public static void showFragment(FragmentActivity activity, Fragment showFragment) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != showFragment) {
                transaction.hide(fragment);
                transaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED);
            } else {
                transaction.show(showFragment);
                transaction.setMaxLifecycle(showFragment, Lifecycle.State.RESUMED);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    public static void exitLastFragment(FragmentActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        // TODO: 2021/12/24 完善退出fragment流程
    }

    public static boolean hasFragment(FragmentActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        return manager.getFragments().size() > 1;
    }
}
