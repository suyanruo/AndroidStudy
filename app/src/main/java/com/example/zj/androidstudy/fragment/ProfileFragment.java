package com.example.zj.androidstudy.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.viewModel.LoginViewModel;

public class ProfileFragment extends Fragment {
  private TextView tvWelcome;
  private LoginViewModel viewModel;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_profile, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

    tvWelcome = view.findViewById(R.id.tv_welcome);

    final NavController navController = Navigation.findNavController(view);
    viewModel.authenticationState.observe(getViewLifecycleOwner(), new Observer<LoginViewModel.AuthenticationState>() {
      @Override
      public void onChanged(LoginViewModel.AuthenticationState authenticationState) {
        switch (authenticationState) {
          case AUTHENTICATED:
            showWelcomeMessage();
            break;
          case UNAUTHENTICATED:
            navController.navigate(R.id.login_fragment);
            break;
        }
      }
    });
  }

  private void showWelcomeMessage() {
    tvWelcome.setText("welcome " + viewModel.userName);
  }
}
