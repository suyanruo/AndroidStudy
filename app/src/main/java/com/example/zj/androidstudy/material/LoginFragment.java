package com.example.zj.androidstudy.material;

import android.os.Bundle;

import com.example.zj.androidstudy.viewModel.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class LoginFragment extends BaseFragment {
    private TextInputLayout mTilUsername;
    private EditText mEtUsername;
    private TextInputLayout mTilPassword;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private Button mBtnRegister;

    private LoginViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return init(inflater.inflate(R.layout.fragment_login, container, false));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        mTilUsername = view.findViewById(R.id.til_username);
        mEtUsername = view.findViewById(R.id.et_username);
        mTilPassword = view.findViewById(R.id.til_password);
        mEtPassword = view.findViewById(R.id.et_password);
        mBtnLogin = view.findViewById(R.id.btn_login);
        mBtnRegister= view.findViewById(R.id.btn_register);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!CommonUtil.isEmail(mTilUsername.getEditText().getText().toString())) {
//                    mTilUsername.setErrorEnabled(true);
//                    mTilUsername.setError("Email patter wrong!");
//                } else {
//                    mTilUsername.setErrorEnabled(false);
//                    mTilPassword.setErrorEnabled(false);
//                    Toast.makeText(getActivity(), "login success", Toast.LENGTH_SHORT).show();
//                }

                viewModel.authenticate(mEtUsername.getText().toString(), mEtPassword.getText().toString());
            }
        });

        final NavController navController = Navigation.findNavController(view);
        final View root = view;

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                viewModel.refuseAuthentication();
                navController.popBackStack(R.id.home_fragment, false);
            }
        });
        viewModel.authenticationState.observe(getViewLifecycleOwner(), new Observer<LoginViewModel.AuthenticationState>() {
            @Override
            public void onChanged(LoginViewModel.AuthenticationState authenticationState) {
                switch (authenticationState) {
                    case AUTHENTICATED:
                        navController.popBackStack();
                        break;
                    case INVALID_AUTHENTICATION:
                        Snackbar.make(root,
                            "invalid_credentials",
                            Snackbar.LENGTH_SHORT
                        ).show();
                        break;
                }
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_login_fragment_to_register_fragment);
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
