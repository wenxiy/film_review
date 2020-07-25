package com.example.film_review.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.film_review.MainActivity;
import com.example.film_review.R;

public class fragment_personal_login extends AppCompatActivity {
    private Button log;
    public static fragment_personal_login ActivityLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        ActivityLogin=this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_loginpage);
        log=findViewById(R.id.log);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getApplication(),fragment_personal_login_in.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("msg","destroy");
    }
}
