package com.example.zj.androidstudy.material;

import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseFragment;
import com.example.zj.androidstudy.tool.CommonUtil;

public class LoginFragment extends BaseFragment {
    private TextInputLayout mTilUsername;
    private EditText mEtUsername;
    private TextInputLayout mTilPassword;
    private Button mBtnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return init(inflater.inflate(R.layout.fragment_login, container, false));
    }

    @Override
    protected void initViews(View view) {
        mTilUsername = view.findViewById(R.id.til_username);
        mEtUsername = view.findViewById(R.id.et_username);
        mTilPassword = view.findViewById(R.id.til_password);
        mBtnLogin = view.findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtil.isEmail(mTilUsername.getEditText().getText().toString())) {
                    mTilUsername.setErrorEnabled(true);
                    mTilUsername.setError("Email patter wrong!");
                } else {
                    mTilUsername.setErrorEnabled(false);
                    mTilPassword.setErrorEnabled(false);
                    Toast.makeText(getActivity(), "login success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initWorkers() {

    }
}
