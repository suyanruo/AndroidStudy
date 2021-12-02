package com.example.zj.androidstudy.viewModel;

import android.content.Context;
import android.text.TextUtils;

import com.example.zj.androidstudy.Constants;
import com.example.zj.androidstudy.tool.EncryptionUtil;
import com.example.zj.androidstudy.tool.SharedPreferenceUtil;

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

  public void authenticate(Context context, String userName, String password) {
    if (passwordIsValidForUserName(context, userName, password)) {
      this.userName = userName;
      authenticationState.setValue(AuthenticationState.AUTHENTICATED);
    } else {
      authenticationState.setValue(AuthenticationState.INVALID_AUTHENTICATION);
    }
  }

  private boolean passwordIsValidForUserName(Context context, String userName, String password) {
    String storePwd = SharedPreferenceUtil.getString(context, Constants.USER_PASSWORD, "");
    if (TextUtils.isEmpty(storePwd)) {
      storePwd = "123456";
    } else {
      storePwd = EncryptionUtil.INSTANCE.getDe3DesString(storePwd);
    }
    return TextUtils.equals(password, storePwd);
  }

  public void refuseAuthentication() {
    authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
  }
}
