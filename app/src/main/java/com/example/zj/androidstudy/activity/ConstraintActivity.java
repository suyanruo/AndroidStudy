package com.example.zj.androidstudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.adapter.RecyclerViewAdapter;

public class ConstraintActivity extends AppCompatActivity {
  RecyclerView recyclerView;
  RecyclerViewAdapter recyclerViewAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_constraint);

    recyclerView = findViewById(R.id.rv_test);
    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    recyclerViewAdapter = new RecyclerViewAdapter(this);
    recyclerView.setAdapter(recyclerViewAdapter);
  }
}
