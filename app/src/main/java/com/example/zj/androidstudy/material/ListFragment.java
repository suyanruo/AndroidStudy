package com.example.zj.androidstudy.material;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.adapter.RecyclerViewAdapter;
import com.example.zj.androidstudy.base.BaseFragment;

public class ListFragment extends BaseFragment {
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initViews(View view) {
        mRecyclerView = (RecyclerView) view;
    }

    @Override
    protected void initWorkers() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity()));
    }
}
