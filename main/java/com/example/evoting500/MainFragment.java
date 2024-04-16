package com.example.evoting500;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.evoting500.AdminLogin;
import com.example.evoting500.Login;
import com.example.evoting500.R;

public class MainFragment extends Fragment {

    private ImageView bgapp;
    private LinearLayout loginbtn;

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        bgapp = view.findViewById(R.id.bgapp);
        loginbtn = view.findViewById(R.id.logins);

        bgapp.animate().translationY(-1000).setDuration(800).setStartDelay(300);

        view.findViewById(R.id.adminLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdminLogin.class));
            }
        });

        view.findViewById(R.id.userLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        return view;
    }
}
