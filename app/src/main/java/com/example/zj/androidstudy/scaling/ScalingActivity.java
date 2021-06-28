package com.example.zj.androidstudy.scaling;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.view.scalingLayout.ScalingLayout;
import com.example.zj.androidstudy.view.scalingLayout.ScalingLayoutListener;
import com.example.zj.androidstudy.view.scalingLayout.State;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

/**
 * Created by mertsimsek on 01/10/2017.
 */

public class ScalingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaling);

        final ImageView fabIcon = findViewById(R.id.fabIcon);
        final LinearLayout filterLayout = findViewById(R.id.filterLayout);
        final ScalingLayout scalingLayout = findViewById(R.id.scalingLayout);

        scalingLayout.setListener(new ScalingLayoutListener() {
            @Override
            public void onCollapsed() {
                ViewCompat.animate(fabIcon).alpha(1).setDuration(150).start();
                ViewCompat.animate(filterLayout).alpha(0).setDuration(150).setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        fabIcon.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        filterLayout.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();
            }

            @Override
            public void onExpanded() {
                ViewCompat.animate(fabIcon).alpha(0).setDuration(200).start();
                ViewCompat.animate(filterLayout).alpha(1).setDuration(200).setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        filterLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        fabIcon.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();
            }

            @Override
            public void onProgress(float progress) {
                if (progress > 0) {
                    fabIcon.setVisibility(View.INVISIBLE);
                }

                if(progress < 1){
                    filterLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        scalingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scalingLayout.getState() == State.COLLAPSED) {
                    scalingLayout.expand();
                }
            }
        });


        findViewById(R.id.rootLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scalingLayout.getState() == State.EXPANDED) {
                    scalingLayout.collapse();
                }
            }
        });

    }
}
