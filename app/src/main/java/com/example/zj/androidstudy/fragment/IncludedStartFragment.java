package com.example.zj.androidstudy.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;

public class IncludedStartFragment extends BaseFragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return init(inflater.inflate(R.layout.fragment_included_start, container, false));
  }

  @Override
  protected void initViews(View view) {
    TextView textView = view.findViewById(R.id.tv_start);
    SpannableStringBuilder spannableString = new SpannableStringBuilder("一二三四五六七八九十");
    spannableString.setSpan(new ClickableSpan() {
      @Override
      public void updateDrawState(@NonNull TextPaint ds) {
        ds.setColor(ContextCompat.getColor(getActivity(), android.R.color.holo_red_dark));
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
      }

      @Override
      public void onClick(@NonNull View widget) {

      }
    }, 2, 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    for (int i = 0; i < 5; i++) {
      spannableString.insert(4, "1");
    }
    textView.setMovementMethod(LinkMovementMethod.getInstance());
    textView.setText(spannableString);
  }

  @Override
  protected void initWorkers() {
  }
}
