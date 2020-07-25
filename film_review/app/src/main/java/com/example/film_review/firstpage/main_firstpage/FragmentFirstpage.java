package com.example.film_review.firstpage.main_firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.film_review.R;
import com.example.film_review.firstpage.search.activity_search;
import com.example.film_review.firstpage.attention.fragment_attention;
import com.example.film_review.firstpage.attention.fragment_unlogin;
import com.example.film_review.firstpage.found.fragment_found;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FragmentFirstpage extends Fragment {
    protected EditText mSearchView;
    private List<Fragment> mList;
    private TabFragmentPagerAdapter adapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private View v;
    private Bundle bundle;
    private String token;
    private String user_id;
    private InputMethodManager mInputMethodManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        bundle=this.getArguments();
        user_id=bundle.getString("user_id");
        token=bundle.getString("token");
        Log.d("FirstPage","3"+user_id);
        Log.d("FirstPage","TOKEN"+token);
//        Toast.makeText(getContext(),"tokendezhi"+token,Toast.LENGTH_LONG).show();

        v=inflater.inflate(R.layout.fragment_main,container,false);
        mSearchView=v.findViewById(R.id.search_text);
        mList=new ArrayList<>();

        //添加搜索跳转CA
        mSearchView.setFocusable(false);
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_search.class);
                startActivity(intent);
            }
        });

        fragment_attention mfragment_attention=fragment_attention.newInstance(token,user_id);
        fragment_found mfragment_found=fragment_found.newInstance(token);
        fragment_unlogin fragment_unlogin= new fragment_unlogin();

        if(token==null) mList.add(fragment_unlogin);
        else mList.add(mfragment_attention);
        mList.add(mfragment_found);

        FragmentManager fragmentManager=getFragmentManager();
        mViewPager=v.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new TabFragmentPagerAdapter(fragmentManager,mList) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        });
        mTabLayout=(TabLayout)v.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中某个tab
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //当tab从选择到未选择
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //已经选中tab后的重复点击tab
            }
        });



        return v;
    }

}
