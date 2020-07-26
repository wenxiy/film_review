package com.example.film_review;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_review.firstpage.main_firstpage.FragmentFirstpage;
import com.example.film_review.personal.API;
import com.example.film_review.personal.Bean;
import com.example.film_review.personal.PersonalFragment;
import com.example.film_review.personal.PersonalLoginFragment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
   //这是为了在登录和注册fragment中传输数据的一个变量，MainActivity是一个中介,(已删除)
    protected FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    private FragmentFirstpage fragmentFirstpage;
    private PersonalLoginFragment mfragment_personal_login;
    private ArrayList<Object> fragments;
    private Fragment mCurrentFragment=null;
    private Bundle mbundle;
    private String token;
    private PersonalFragment mfragment_personal;
    private Intent mIntent;
    public static MainActivity sMainActivity;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sMainActivity=this;

        Fresco.initialize(this);

//        拿到token
        Bundle bundle= this.getIntent().getExtras();
        if (bundle!=null) {token=bundle.getString("token");
        user_id=bundle.getString("user_id");
        Log.d("2","user_id:"+user_id);}

        setContentView(R.layout.activity_main);

        bottomNavigationView =findViewById(R.id.bottom_content);
        bottomNavigationView.setItemIconTintList(null);


        initFragments();
        initListenner();
        bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(0).getItemId());
       // gethomepage();//个人界面的网络请求

    }

    private void initFragments() {

        //        将token又传到bundle中
        mbundle=new Bundle();
        mbundle.putString("token",token);
        mbundle.putString("user_id",user_id);

//        activity与fragment之间通过bundle和setArguments传递数据
        fragmentFirstpage = new FragmentFirstpage();
        fragmentFirstpage.setArguments(mbundle);
        mfragment_personal=new PersonalFragment();
        mfragment_personal.setArguments(mbundle);


        fragmentManager=getSupportFragmentManager();
//        switchFragment(fragmentFirstpage);
        if(fragments!=null){
            fragments.clear();
        }else
        fragments=new ArrayList<>();
        fragments.add(fragmentFirstpage);
        fragments.add(mfragment_personal);
        Log.i("MainActivity","Start");
    }

    private void initListenner() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.first_page){
                    showFragment(0);
                    Log.i("MainActivity","firstpage");

                }
                else if(menuItem.getItemId()==R.id.product){
                    Log.i("MainActivity","product");
                }
                else if(menuItem.getItemId()==R.id.personal){
                    if(token==null){
                        mIntent=new Intent();
                        mIntent.setClass(getApplicationContext(), PersonalLoginFragment.class);
                        startActivity(mIntent);
                    }
                    else showFragment(1);
                    Log.d("MainActivity","personal");
                    Log.d("TOKEN","token"+token);
                }

                return true;
            }


        });
    }
    private void showFragment(int position) {

        if(fragments!=null&& fragments.size()>0){
            Fragment fragment= (Fragment) fragments.get(position);
            if(null != fragment&& mCurrentFragment!=fragment){
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                if(mCurrentFragment!=null){
                    transaction.hide(mCurrentFragment);
                }
                mCurrentFragment=fragment;
                if(!fragment.isAdded()){
                    transaction.add(R.id.fragment_container,fragment);
                }else{
                    transaction.show(fragment);
                }
                transaction.commit();
            }
        }

        Log.i("MainActivity", "switchFragment: ");
    }
    public void gethomepage() {//这是个人界面拉取头像等信息的网络请求，还没调试先不要删除 我还没将其加入调用，不会报错
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("114.215.201.204:9091")//记得改ip
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.e("TAG","1");
        API api=retrofit.create(API.class);
        Call<Bean> task=api.gethomepage();//创建网络接口的实例
        task.enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                Log.e("TAG","2");
            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {
                Log.e("TAG","3");
            }
        });

    }
}
