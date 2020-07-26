package com.example.film_review.personal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.film_review.MainActivity;
import com.example.film_review.R;
import com.example.film_review.personal.bean.PostLoginBean;
import com.example.film_review.personal.bean.PostToken;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalLogActivity extends AppCompatActivity {
    public Button log_in;
    public TextView don_log_in;
    public EditText user_id;
    public EditText user_passwords;
    private PostToken Post_rlogin;
    private String s;
    private String UserId;
    public static PersonalLogActivity ActivityLoginIn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        log_in=findViewById(R.id.log_in);
        user_passwords=findViewById(R.id.user_passwords);
        don_log_in=findViewById(R.id.don_log_in);
        user_id=findViewById(R.id.user_ID);
        Intent it2=getIntent();
        String a=it2.getStringExtra("key");
        user_id.setText(a);

        ActivityLoginIn=this;

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mpasswords=user_passwords.getText().toString();
                if(user_id.equals("")||mpasswords.equals("")) {
                    Toast.makeText(PersonalLogActivity.this,"请输入完整的账户和密码",Toast.LENGTH_SHORT).show();
                }
                else{
                    getPost_login();
                }
            }
        });
        don_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(PersonalLogActivity.this, RegisteredFragment.class);
                startActivity(intent);
            }
        });
    }
    public void getPost_login(){
        String mpasswords=user_passwords.getText().toString();
        String muser_id=user_id.getText().toString();
        PostLoginBean post_login=new PostLoginBean();
        post_login.setPassword(mpasswords);
        post_login.setUser_id(muser_id);
        Retrofit lretrofit=new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API lapi=lretrofit.create(API.class);
        Call<PostToken> task=lapi.getPost_login(post_login);
        task.enqueue(new Callback<PostToken>() {
            @Override
            public void onResponse(Call<PostToken> call, Response<PostToken> response) {
                int code=response.code();
                if(code== HttpURLConnection.HTTP_OK){
                    Post_rlogin=response.body();

//                    获得登录界面的token，并把它传进mainactivity，后面要用
                    s=Post_rlogin.getToken();
                    UserId=muser_id;
                    Log.d("TAG","onResponse--->"+s);
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("token",s);
                    bundle.putString("user_id",UserId);
                    intent.putExtras(bundle);
                    Log.d("TAG","user_id"+UserId);

                    intent.setClass(PersonalLogActivity.this,MainActivity.class);

                    startActivity(intent);
                    PersonalLoginActivity.ActivityLogin.finish();
                    MainActivity.sMainActivity.finish();
                    finish();
                }
                Log.e("TAG","200");
                if(code==HttpURLConnection.HTTP_UNAUTHORIZED){
                    Toast.makeText(PersonalLogActivity.this,"用户名和密码错误",Toast.LENGTH_SHORT).show();
                    Log.e("TAG","401");
                }
                if(code==HttpURLConnection.HTTP_BAD_REQUEST){
                    Toast.makeText(PersonalLogActivity.this,"参数不全，客户端语法有问题",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<PostToken> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
