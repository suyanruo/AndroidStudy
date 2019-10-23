package com.example.zj.androidstudy.base;

import androidx.fragment.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment {

    protected abstract void initViews(View view);
    protected abstract void initWorkers();

    public View init(View view) {
        initViews(view);
        initWorkers();
        return view;
    }
}
