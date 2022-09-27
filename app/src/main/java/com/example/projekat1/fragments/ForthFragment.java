package com.example.projekat1.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projekat1.activities.LoginActivity;
import com.example.projekat1.R;

public class ForthFragment extends Fragment {
    public ForthFragment() {
        super(R.layout.fragment_forth);
    }

    private TextView usernameTv;
    private TextView emailTv;
    private Button logoutBtn;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        init();
        return view;
    }

    private void init() {
        initView();
        initDefaultValues();
        initListeners();
    }

    private void initDefaultValues() {
        usernameTv.setText(LoginActivity.savedUsername);
        emailTv.setText(LoginActivity.savedEmail);
        emailTv.setPaintFlags(emailTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void initListeners() {
        logoutBtn.setOnClickListener(e -> {
            Intent intent = new Intent();
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        });
    }

    private void initView(){
        usernameTv = view.findViewById(R.id.username);
        emailTv = view.findViewById(R.id.email);
        logoutBtn = view.findViewById(R.id.logoutButton);
    }
}
