package com.example.zj.androidstudy.fragment;

import android.content.Intent;
import android.net.Uri;

import android.view.View;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.activity.BinderActivity;
import com.example.zj.androidstudy.activity.ConstraintActivity;
import com.example.zj.androidstudy.activity.CustomViewActivity;
import com.example.zj.androidstudy.activity.DialogActivity;
import com.example.zj.androidstudy.activity.H5ToAppActivity;
import com.example.zj.androidstudy.activity.MessengerActivity;
import com.example.zj.androidstudy.liveData.NameActivity;
import com.example.zj.androidstudy.picture.PictureActivity;
import com.example.zj.androidstudy.viewpager.ViewPager2Activity;
import com.example.zj.androidstudy.viewpager.ViewPagerActivity;
import com.example.zj.androidstudy.autoFillCode.AutoFillSmsCodeActivity;
import com.example.zj.androidstudy.baidu.MapActivity;
import com.example.zj.androidstudy.base.BaseFragment;
import com.example.zj.androidstudy.bigImage.LargeImageViewActivity;
import com.example.zj.androidstudy.camera.CameraActivity;
import com.example.zj.androidstudy.contentProvider.ContentActivity;
import com.example.zj.androidstudy.cutOut.CutOutActivity;
import com.example.zj.androidstudy.download.DownloadActivity;
import com.example.zj.androidstudy.firebase.FirebaseActivity;
import com.example.zj.androidstudy.lazyLoad.LazyFragmentActivity;
import com.example.zj.androidstudy.markdown.MarkdownActivity;
import com.example.zj.androidstudy.material.MaterialDesignActivity;
import com.example.zj.androidstudy.media.PhotoActivity;
import com.example.zj.androidstudy.net.OkhttpActivity;
import com.example.zj.androidstudy.pdf.PdfSearchActivity;
import com.example.zj.androidstudy.puzzle.PuzzleActivity;
import com.example.zj.androidstudy.scrollAndViewpager.ScrollViewPagerActivity;
import com.example.zj.androidstudy.service.ServiceActivity;
import com.example.zj.androidstudy.shareElement.ShareElementActivity;
import com.example.zj.androidstudy.thread.ThreadActivity;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class HomeFragment extends BaseFragment {

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_home;
  }

  @Override
  protected void initViews(View view) {
    view.findViewById(R.id.btn_binder).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), BinderActivity.class));
      }
    });

    view.findViewById(R.id.btn_material_design).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), MaterialDesignActivity.class));
      }
    });

    view.findViewById(R.id.btn_content_provider).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), ContentActivity.class));
      }
    });

    view.findViewById(R.id.btn_photo).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), PhotoActivity.class));
      }
    });

    view.findViewById(R.id.btn_service).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), ServiceActivity.class));
      }
    });

    view.findViewById(R.id.btn_map).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), MapActivity.class));
      }
    });

    view.findViewById(R.id.btn_load_big_image).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), LargeImageViewActivity.class));
      }
    });
    view.findViewById(R.id.btn_puzzle).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // 隐式调用
        Intent intent = new Intent(getActivity(), PuzzleActivity.class);
        intent.addCategory("com.example.zj.puzzle");
        startActivity(intent);
      }
    });
    view.findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), CameraActivity.class));
      }
    });
    view.findViewById(R.id.btn_custom_view).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), CustomViewActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_dialog).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), DialogActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_constraint).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), ConstraintActivity.class));
      }
    });

    view.findViewById(R.id.btn_activity_viewpager).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), ViewPagerActivity.class));
      }
    });

    view.findViewById(R.id.btn_activity_viewpager2).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), ViewPager2Activity.class));
      }
    });

    view.findViewById(R.id.btn_activity_auto_fill_sms).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), AutoFillSmsCodeActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_share_element).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), ShareElementActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_cut_out).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), CutOutActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_scroll_viewpager).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), ScrollViewPagerActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_pdf).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), PdfSearchActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_markdown).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), MarkdownActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_firebase).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), FirebaseActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_okhttp).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), OkhttpActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_thread).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().startActivity(new Intent(getActivity(), ThreadActivity.class));

      }
    });
    view.findViewById(R.id.btn_activity_h5_jump).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getActivity().startActivity(new Intent(getActivity(), H5ToAppActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_messenger).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getActivity().startActivity(new Intent(getActivity(), MessengerActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_download).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getActivity().startActivity(new Intent(getActivity(), DownloadActivity.class));
      }
    });
    view.findViewById(R.id.btn_activity_lazy_load).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getActivity().startActivity(new Intent(getActivity(), LazyFragmentActivity.class));
      }
    });

    view.findViewById(R.id.btn_activity_picture).setOnClickListener(view12 ->
        getActivity().startActivity(new Intent(getActivity(), PictureActivity.class)));

    view.findViewById(R.id.btn_activity_live_data).setOnClickListener(view1 ->
        getActivity().startActivity(new Intent(getActivity(), NameActivity.class)));

    /*-----------------Navigation-----------------*/
    view.findViewById(R.id.btn_fragment_login).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment);
      }
    });
    view.findViewById(R.id.btn_fragment_pay).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_chooseRecipientFragment);
      }
    });
    view.findViewById(R.id.btn_fragment_include_start).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_includedStart, null));
    view.findViewById(R.id.btn_fragment_profile).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_profileFragment);
      }
    });
  }

  @Override
  protected void initWorkers() {
  }

  /**
   * 启动WhatsApp
   */
  private void startWhatsApp() {
    Uri uri = Uri.parse("whatsapp://send?phone=8618709251910");
    startActivity(new Intent(Intent.ACTION_VIEW,uri));
  }
}
