package com.example.film_review.personal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.film_review.R;
import com.example.film_review.personal.bean.PostBean;
import com.example.film_review.personal.bean.PostData;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisteredFragment extends AppCompatActivity {
    public Button mregistered;
    public final static String  NAME_KEY = "id";
    public int a=2;//用于判断网络请求是否成功
    public EditText mEditText1;//这是注册昵称
    public EditText mEditText2;//这是注册密码
    public static RegisteredFragment ActivityRegistered;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ActivityRegistered=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_personal_registered);
        mregistered=findViewById(R.id.register);
        mEditText1=findViewById(R.id.user_name);
        mEditText2=findViewById(R.id.user_password);
        mregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName= mEditText1.getText().toString();
                String mPassword= mEditText2.getText().toString();
                if(mName.equals("")||mPassword.equals(""))
                {
                    Toast.makeText(RegisteredFragment.this,"请完整输入用户名和密码",Toast.LENGTH_SHORT).show();
                }
                else{
                    getrPostdata();
                }
            }
        });
    }
    public void getrPostdata(){
        PostData postdata=new PostData();
        String mName= mEditText1.getText().toString();
        String mPassword= mEditText2.getText().toString();
        postdata.setName(mName);//发送的用户名
        postdata.setPassword(mPassword);//发送的密码
        Log.e("TAG","00");
        Retrofit pretrofit=new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.e("TAG","5");
        API api=pretrofit.create(API.class);
        Call<PostBean> task=api.getrPostdata(postdata);
        Log.e("TAG","6");
        task.enqueue(new Callback<PostBean>() {
            @Override
            public void onResponse(Call<PostBean> call, Response<PostBean> response) {
                int code=response.code();
               // Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                if(code== HttpURLConnection.HTTP_OK) {
                Log.d("TAG","onResponse--->"+response.body().toString());
                Toast.makeText(RegisteredFragment.this,"已为您自动跳转至登录界面，记得记住用户名ID哦",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();//这是跳转的Intent
                    intent.putExtra("key",response.body().toString());
                    intent.setClass(RegisteredFragment.this, PersonalLogActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (code==HttpURLConnection.HTTP_UNAUTHORIZED)
                {
                    Toast.makeText(RegisteredFragment.this,"用户名和密码输入格式不对哦",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<PostBean> call, Throwable t) {
             //   Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                Log.e("TAG","Failure");
                a=1;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
