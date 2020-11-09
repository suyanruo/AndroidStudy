package com.example.zj.androidstudy.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;
import com.example.zj.androidstudy.viewModel.*;
import com.example.zj.androidstudy.viewModel.RegistrationViewModel.RegistrationState;

public class ChooseUserPasswordFragment extends BaseFragment {
  private LoginViewModel loginViewModel;
  private RegistrationViewModel registrationViewModel;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return init(inflater.inflate(R.layout.fragment_choose_user_password, container, false));
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ViewModelProvider provider = new ViewModelProvider(requireActivity());
    registrationViewModel = provider.get(RegistrationViewModel.class);
    loginViewModel = provider.get(LoginViewModel.class);

    final NavController navController = Navigation.findNavController(view);

    final EditText etName = view.findViewById(R.id.et_username);
    final EditText etPassword = view.findViewById(R.id.et_password);

    // When the register button is clicked, collect the current values from
    // the two edit texts and pass to the ViewModel to complete registration.
    view.findViewById(R.id.btn_register_and_login).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        registrationViewModel.createAccountAndLogin(
            etName.getText().toString(),
            etPassword.getText().toString()
        );
      }
    });

    // RegistrationViewModel updates the registrationState to
    // REGISTRATION_COMPLETED when ready, and for this example, the username
    // is accessed as a read-only property from RegistrationViewModel and is
    // used to directly authenticate with loginViewModel.
    registrationViewModel.getRegistrationState().observe(getViewLifecycleOwner(), new Observer<RegistrationState>() {
      @Override
      public void onChanged(RegistrationState registrationState) {
        if (registrationState == RegistrationState.REGISTRATION_COMPLETED) {
          // Here we authenticate with the token provided by the ViewModel
          // then pop back to the profie_fragment, where the user authentication
          // status will be tested and should be authenticated.
          String authToken = registrationViewModel.getAuthToken();
          String[] infos = authToken.split("$");
          loginViewModel.authenticate(infos[0], infos[1]);
          navController.popBackStack(R.id.profile_fragment, false);
        }
      }
    });

    // If the user presses back, cancel the user registration and pop back
    // to the login fragment. Since this ViewModel is shared at the activity
    // scope, its state must be reset so that it will be in the initial
    // state if the user comes back to register later.
    requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
      @Override
      public void handleOnBackPressed() {
        registrationViewModel.userCancelledRegistration();
        navController.popBackStack(R.id.login_fragment, false);
      }
    });
  }

  @Override
  protected void initViews(View view) {
  }

  @Override
  protected void initWorkers() {
  }
}
