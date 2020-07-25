package com.example.film_review.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.film_review.MainActivity;
import com.example.film_review.R;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fragment_personal_login_in extends AppCompatActivity {
    public Button log_in;
    public TextView don_log_in;
    public EditText user_id;
    public EditText user_passwords;
    private Post_rlogin Post_rlogin;
    private String s;
    private String UserId;
    public static fragment_personal_login_in ActivityLoginIn;

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
                    Toast.makeText(fragment_personal_login_in.this,"请输入完整的账户和密码",Toast.LENGTH_SHORT).show();
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
                intent.setClass(fragment_personal_login_in.this,fragment_personal_registered.class);
                startActivity(intent);
            }
        });
    }
    public void getPost_login(){
        String mpasswords=user_passwords.getText().toString();
        String muser_id=user_id.getText().toString();
        Post_login post_login=new Post_login();
        post_login.setPassword(mpasswords);
        post_login.setUser_id(muser_id);
        Retrofit lretrofit=new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API lapi=lretrofit.create(API.class);
        Call<Post_rlogin> task=lapi.getPost_login(post_login);
        task.enqueue(new Callback<Post_rlogin>() {
            @Override
            public void onResponse(Call<Post_rlogin> call, Response<Post_rlogin> response) {
                int code=response.code();
                if(code== HttpURLConnection.HTTP_OK){
                    Post_rlogin=response.body();

//                    获得登录界面的token，并把它传进mainactivity，我后面要用
                    s=Post_rlogin.getToken();
                    UserId=muser_id;
                    Log.d("TAG","onResponse--->"+s);
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putString("token",s);
                    bundle.putString("user_id",UserId);
                    intent.putExtras(bundle);
                    Log.d("TAG","user_id"+UserId);

                    intent.setClass(fragment_personal_login_in.this,MainActivity.class);

                    startActivity(intent);
                    fragment_personal_login.ActivityLogin.finish();
                    MainActivity.sMainActivity.finish();
                    finish();
                }
                Log.e("TAG","200");
                if(code==HttpURLConnection.HTTP_UNAUTHORIZED){
                    Toast.makeText(fragment_personal_login_in.this,"用户名和密码错误",Toast.LENGTH_SHORT).show();
                    Log.e("TAG","401");
                }
                if(code==HttpURLConnection.HTTP_BAD_REQUEST){
                    Toast.makeText(fragment_personal_login_in.this,"参数不全，客户端语法有问题",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Post_rlogin> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
