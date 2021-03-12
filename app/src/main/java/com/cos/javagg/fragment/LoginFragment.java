package com.cos.javagg.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cos.javagg.R;

public class LoginFragment extends Fragment {
    private TextView tvForgetPassword, tvJoin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);

        tvForgetPassword = view.findViewById(R.id.tv_forgetpassword);
        tvJoin = view.findViewById(R.id.tv_join);

        tvForgetPassword.setText(Html.fromHtml( "<font color=\"#2a7de2\"><u>비밀번호를 잊으셨나요</u></font>"));
        tvJoin.setText(Html.fromHtml( "<font color=\"#2a7de2\"><u>회원가입하기</u></font>"));



        return view;
    }
}