package com.example.film_review.personal;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.HashMap;
public class Myadapter extends FragmentPagerAdapter {
    private HashMap<Integer,Fragment> mFragmentHashMap =new HashMap<>();
    private String user_id;
    private String token;

    public Myadapter(FragmentManager fm,String token,String user_id,int tabCount){
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.user_id=user_id;
        this.token=token;
    }
    @Override
    public Fragment getItem(int position) {
        return createFragment(position);
    }
    private Fragment createFragment(int pos){
        Fragment fragment = mFragmentHashMap.get(pos);
        if(fragment==null)
        {
            switch (pos){
                case 0:
                    fragment=new My_my();
                    break;
                case 1:
                   fragment =My_favorite.newInstance(token,user_id);
                    Log.i("fragment", "fragment2");
                    break;
                case 2:
                    fragment = new My_album();
                    Log.i("fragment", "fragment3");
                    break;
            }
            mFragmentHashMap.put(pos,fragment);
        }
        return fragment;

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "我的";
        }
        if (position == 1) {
            return "收藏";
        }

        return "影单";

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
