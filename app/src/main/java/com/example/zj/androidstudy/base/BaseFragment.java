package com.example.zj.androidstudy.base;

import androidx.fragment.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment {
    private boolean isLoaded = false;

    protected abstract void initViews(View view);
    protected abstract void initWorkers();

    public View init(View view) {
        initViews(view);
        initWorkers();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        judgeLazyInit();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        judgeLazyInit();
    }

    private void judgeLazyInit() {
        if (!isLoaded && !isHidden()) {
            lazyInit();
            isLoaded = true;
        }
    }

    /**
     * 子类通过覆写此方法实现懒加载
     */
    public void lazyInit() {}
}
