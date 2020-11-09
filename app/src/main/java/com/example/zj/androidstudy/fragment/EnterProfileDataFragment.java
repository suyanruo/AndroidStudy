package com.example.zj.androidstudy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;
import com.example.zj.androidstudy.viewModel.RegistrationViewModel;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.zj.androidstudy.viewModel.RegistrationViewModel.RegistrationState;

/**
 * Created on 2020/9/22.
 */
public class EnterProfileDataFragment extends BaseFragment {
  private RegistrationViewModel registrationViewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return init(inflater.inflate(R.layout.fragment_enter_profile_data, container, false));
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    registrationViewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
    final NavController navController = Navigation.findNavController(view);

    // When the next button is clicked, collect the current values from the two edit texts
    // and pass to the ViewModel to store.
    final EditText etFullName = view.findViewById(R.id.et_fullname);
    final EditText etBio = view.findViewById(R.id.et_bio);
    view.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String name = etFullName.getText().toString();
        String bio = etBio.getText().toString();
        registrationViewModel.collectProfileData(name, bio);
      }
    });

    // RegistrationViewModel updates the registrationState to
    // COLLECT_USER_PASSWORD when ready to move to the choose username and
    // password screen.
    registrationViewModel.getRegistrationState().observe(getViewLifecycleOwner(), new Observer<RegistrationState>() {
      @Override
      public void onChanged(RegistrationViewModel.RegistrationState registrationState) {
        if (registrationState == RegistrationState.COLLECT_USER_PASSWORD) {
          navController.navigate(R.id.move_to_choose_user_password);
        }
      }
    });

    // If the user presses back, cancel the user registration and pop back
    // to the login fragment. Since this ViewModel is shared at the activity
    // scope, its state must be reset so that it is in the initial state if
    // the user comes back to register later.
    requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
        new OnBackPressedCallback(true) {
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
