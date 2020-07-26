package com.example.film_review.personal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film_review.R;

public class MyFragment extends Fragment {
    public RecyclerView myRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View v=inflater.inflate(R.layout.fragment_personal_my,null);
        //myRecyclerView=v.findViewById(R.id.my_recyclerview);

        return v;
    }

}
