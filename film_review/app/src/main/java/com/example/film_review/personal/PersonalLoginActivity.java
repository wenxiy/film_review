package com.example.film_review.personal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.film_review.R;

public class PersonalLoginActivity extends AppCompatActivity {
    private Button log;
    public static PersonalLoginActivity ActivityLogin;
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
                intent.setClass(getApplication(), PersonalLogActivity.class);
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
