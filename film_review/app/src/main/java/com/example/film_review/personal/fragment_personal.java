package com.example.film_review.personal;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.film_review.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.tabs.TabLayout;

public class fragment_personal extends Fragment {
    private ViewPager mViewPager;
    private Myadapter mMyadapter;
    //Context mContext;
    private TabLayout mTabs ;
    private SimpleDraweeView msimpledraweeView;
    private Bundle bundle;
    private String user_id;
    private String token;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bundle=this.getArguments();
        user_id=bundle.getString("user_id");
        token=bundle.getString("token");
        Log.d("FirstPage","3"+user_id);
        Log.d("FirstPage","TOKEN"+token);
        View v=inflater.inflate(R.layout.fragment_personal,null);
        msimpledraweeView=v.findViewById(R.id.avatar);
        mViewPager=v.findViewById(R.id.viewparper1);
        mTabs =v.findViewById(R.id.tabs);
        initfragment();
        initView();
        return v;
    }
    public void initView()
    {
        //   mTabs=findViewById(R.id.tabs);
        //   mMyadapter=new Myadapter(getSupportFragmentManager());
        Uri imageUri= Uri.parse("http://placekitten.com/300/200");
        msimpledraweeView.setImageURI(imageUri);
    }
    public void initfragment(){
        mTabs.addTab(mTabs.newTab().setText("我的"));
        mTabs.addTab(mTabs.newTab().setText("影单"));
        mTabs.addTab(mTabs.newTab().setText("收藏"));
        mViewPager.setAdapter(new Myadapter(getFragmentManager(),token,user_id, mTabs.getTabCount()));

    }
}
