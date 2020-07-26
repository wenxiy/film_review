package com.example.film_review.personal;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.film_review.R;
import com.example.film_review.personal.adapter.Myadapter;
import com.example.film_review.personal.bean.Bean;
import com.example.film_review.personal.bean.PostBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.tabs.TabLayout;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalFragment extends Fragment {
    private ViewPager mViewPager;
    private Myadapter mMyadapter;
    private TabLayout mTabs;
    private SimpleDraweeView msimpledraweeView;
    private Bundle bundle;
    private String user_id;
    private String token;
    private Bean personbean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bundle = this.getArguments();
        user_id = bundle.getString("user_id");
        token = bundle.getString("token");
        Log.d("FirstPage", "3" + user_id);
        Log.d("FirstPage", "TOKEN" + token);
        View v = inflater.inflate(R.layout.fragment_personal, null);
        msimpledraweeView = v.findViewById(R.id.avatar);
        mViewPager = v.findViewById(R.id.viewparper1);
        mTabs = v.findViewById(R.id.tabs);
        initfragment();
        initView();
        return v;
    }

    public void initView() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("TAG","debug.wenxiy0");
        API api =retrofit.create(API.class);
        Log.d("TAG","debug.wenxiy1");
        Call<Bean> task=api.gethomepage();
        Log.d("TAG","debug.wenxiy2");
        task.enqueue(new Callback<Bean>() {
            @Override
            public void onResponse(Call<Bean> call, Response<Bean> response) {
                int code=response.code();
                if (code== HttpsURLConnection.HTTP_OK){
                    personbean=response.body();
                    Log.d("Tag",personbean.getUser_picture());
                    msimpledraweeView.setImageURI(personbean.getUser_picture());
                }
            }

            @Override
            public void onFailure(Call<Bean> call, Throwable t) {
                Uri imageUri = Uri.parse("http://placekitten.com/300/200");
                msimpledraweeView.setImageURI(imageUri);
            }
        });
        //   mTabs=findViewById(R.id.tabs);
        //   mMyadapter=new Myadapter(getSupportFragmentManager());
       Uri imageUri = Uri.parse("http://placekitten.com/300/200");
       // msimpledraweeView.setImageURI(imageUri);
    }

    public void initfragment() {
        mTabs.addTab(mTabs.newTab().setText("我的"));
        mTabs.addTab(mTabs.newTab().setText("影单"));
        mTabs.addTab(mTabs.newTab().setText("收藏"));
        mViewPager.setAdapter(new Myadapter(getFragmentManager(), token, user_id, mTabs.getTabCount()));

    }
}
