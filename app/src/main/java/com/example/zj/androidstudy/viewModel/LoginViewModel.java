package com.example.zj.androidstudy.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created on 2020/9/18.
 */
public class LoginViewModel extends ViewModel {

  public enum AuthenticationState {
    UNAUTHENTICATED,        // Initial state, the user needs to authenticate
    AUTHENTICATED,          // The user has authenticated successfully
    INVALID_AUTHENTICATION  // Authentication failed
  }

  public final MutableLiveData<AuthenticationState> authenticationState = new MutableLiveData<>();
  public String userName;

  public LoginViewModel() {
    authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
    this.userName = "";
  }

  public void authenticate(String userName, String password) {
    if (passwordIsValidForUserName(userName, password)) {
      this.userName = userName;
      authenticationState.setValue(AuthenticationState.AUTHENTICATED);
    } else {
      authenticationState.setValue(AuthenticationState.INVALID_AUTHENTICATION);
    }
  }

  private boolean passwordIsValidForUserName(String userName, String password) {
    return true;
  }

  public void refuseAuthentication() {
    authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
  }
}
