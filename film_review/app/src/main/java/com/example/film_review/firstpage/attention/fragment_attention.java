package com.example.film_review.firstpage.attention;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.film_review.R;
import com.example.film_review.firstpage.RetrofitAPI;
import com.example.film_review.firstpage.found.groundsData;
import com.example.film_review.firstpage.review_content.ContentActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fragment_attention extends Fragment {
    public RecyclerView list;
    public List<AttentionItemData> mData;
    private LinearLayoutManager mLinearLayoutManager;
    private ListViewAdapter mAdapter;
    private String token;
    private View v;
    private String user_id;
    private SwipeRefreshLayout refreshLayout;

    public static fragment_attention newInstance(String token, String user_id) {

        Bundle args = new Bundle();
        args.putString("token", token);
        args.putString("user_id", user_id);

        fragment_attention fragment = new fragment_attention();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        token = getArguments().getString("token");
        user_id = getArguments().getString("user_id");
        Log.d("4", "id" + user_id);
        v = inflater.inflate(R.layout.fragment_attention, null);

        list = v.findViewById(R.id.recyclerview);

//        mLinearLayoutManager = new LinearLayoutManager(getActivity());
//        list.setLayoutManager(mLinearLayoutManager);
//        //创建适配器

        refreshLayout = v.findViewById(R.id.attention_refreshLayout);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                Log.d("TAG", "下拉刷新");
            }
        });
        initData();

        return v;
    }

    private void initData() {

        mData = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI API = retrofit.create(RetrofitAPI.class);
        Call<List<AttentionItemData>> task = API.getAttention(token, user_id);
        task.enqueue(new Callback<List<AttentionItemData>>() {
            @Override
            public void onResponse(Call<List<AttentionItemData>> call, Response<List<AttentionItemData>> response) {

                int code = response.code();
                mData = response.body();
                mLinearLayoutManager = new LinearLayoutManager(getActivity());
                list.setLayoutManager(mLinearLayoutManager);
                mAdapter = new ListViewAdapter(mData);
                list.setAdapter(mAdapter);
                refreshLayout.setRefreshing(false);
                mAdapter.setOnItemClickListener(new ListViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                                将intent与目标activity联系
                        Intent intent = new Intent(getActivity(), ContentActivity.class);
//                        建立一个bundle储存一些要传到activity的数据
                        Bundle bundle = new Bundle();
//                        传入影评请求是需要的id
                        bundle.putString("user_id", mData.get(position).getUser_id());
                        bundle.putInt("Id", mData.get(position).getReview_id());
                        bundle.putString("content", mData.get(position).getContent());
                        bundle.putString("UserName", mData.get(position).getName());
                        bundle.putString("reviewTitle", mData.get(position).getTitle());
                        bundle.putString("reviewTag", mData.get(position).getTag());
                        bundle.putString("reviewPicture", mData.get(position).getPicture());
                        bundle.putString("reviewTime", mData.get(position).getTime());
                        bundle.putString("reviewIcon", mData.get(position).getUser_picture());
                        bundle.putString("token", token);
//                        将bundle放进Intent
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                });
                Log.d("TAG", "response-->" + response.body());


            }

            @Override
            public void onFailure(Call<List<AttentionItemData>> call, Throwable t) {
                Log.e("TAG", "FAILURE");
                t.printStackTrace();
                refreshLayout.setRefreshing(false);
            }
        });


    }

}
