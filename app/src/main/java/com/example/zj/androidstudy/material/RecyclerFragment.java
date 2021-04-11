package com.example.zj.androidstudy.material;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class RecyclerFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private RecyclerViewNewAdapter mRecyclerViewNewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler;
    }

    @Override
    protected void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerViewNewAdapter = new RecyclerViewNewAdapter(mData);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setAdapter(mRecyclerViewNewAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerViewNewAdapter.setOnItemClickListener(new RecyclerViewNewAdapter.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(final View view, int position) {
                // 设置点击动画
                view.animate().translationZ(15f).setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                view.animate().translationZ(1f).setDuration(500).start();
                            }
                        }).start();
            }
        });
    }

    @Override
    protected void initWorkers() {
        mData.add("Recycler1");
        mData.add("Recycler2");
        mData.add("Recycler3");
        mData.add("Recycler4");
        mData.add("Recycler5");
        mRecyclerViewNewAdapter.notifyDataSetChanged();
    }
}
