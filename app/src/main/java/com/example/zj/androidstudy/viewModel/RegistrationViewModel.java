package com.example.zj.androidstudy.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created on 2020/9/22.
 */
public class RegistrationViewModel extends ViewModel {
  public enum RegistrationState {
    COLLECT_PROFILE_DATA,
    COLLECT_USER_PASSWORD,
    REGISTRATION_COMPLETED
  }

  private MutableLiveData<RegistrationState> registrationState = new MutableLiveData<>(RegistrationState.COLLECT_PROFILE_DATA);

  public MutableLiveData<RegistrationState> getRegistrationState() {
    return registrationState;
  }

  // Simulation of real-world scenario, where an auth token may be provided as
  // an alternate authentication mechanism instead of passing the password
  // around. This is set at the end of the registration process.
  private String authToken;

  public String getAuthToken() {
    return authToken;
  }

  public void collectProfileData(String name, String bio) {
    // ... validate and store data

    // Change State to collecting username and password
    registrationState.setValue( RegistrationState.COLLECT_USER_PASSWORD);
  }

  public void createAccountAndLogin(String username, String password) {
    // ... create account
    // ... authenticate
    this.authToken = username + "$" + password; // token

    // Change State to registration completed
    registrationState.setValue(RegistrationState.REGISTRATION_COMPLETED);
  }

  public boolean userCancelledRegistration() {
    // Clear existing registration data
    registrationState.setValue(RegistrationState.COLLECT_PROFILE_DATA);
    authToken = "";
    return true;
  }

}
