package com.example.zj.androidstudy.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return init(view);
    }

    public View init(View view) {
        initViews(view);
        initWorkers();
        return view;
    }

    protected abstract int getLayoutId();
    protected abstract void initViews(View view);
    protected abstract void initWorkers();
}
