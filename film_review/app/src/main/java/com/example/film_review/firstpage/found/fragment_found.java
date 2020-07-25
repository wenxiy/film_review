package com.example.film_review.firstpage.found;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.film_review.R;
import com.example.film_review.firstpage.RetrofitAPI;
import com.example.film_review.firstpage.attention.fragment_attention;
import com.example.film_review.firstpage.review_content.ContentActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL;

public class fragment_found extends Fragment {
    private ArrayList<groundsData> mData;
    private RecyclerView list;
    private StaggerAdapter mAdapter;
    private String token;
    private SwipeRefreshLayout refreshLayout;


    public static fragment_found newInstance(String token) {

        Bundle args = new Bundle();
        args.putString("token",token);

        fragment_found fragment = new fragment_found();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fagment_found, null);
        token=getArguments().getString("token");

        list = v.findViewById(R.id.stagger_recyclerview);
        //      准备布局管理器
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, VERTICAL);
////        设置布局管理器
//        layoutManager.setReverseLayout(false);
////        设置布局管理器到recyclerview里
//        list.setLayoutManager(layoutManager);

        refreshLayout=v.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                Log.d("TAG","下拉刷新");
            }
        });
//        refreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(true);
//            }
//        });
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
        Call<List<groundsData>> task = API.getData();
        task.enqueue(new Callback<List<groundsData>>() {
            @Override
            public void onResponse(Call<List<groundsData>> call, Response<List<groundsData>> response) {
                mData.addAll(response.body());
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, VERTICAL);
//        设置布局管理器
                layoutManager.setReverseLayout(false);
//        设置布局管理器到recyclerview里
                list.setLayoutManager(layoutManager);
//                    创建适配器
                mAdapter = new StaggerAdapter(mData);
                list.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
                Log.d("TAG","shuju:"+response.body().size());

                mAdapter.setOnItemClickListener(new StaggerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                        将intent与目标activity联系
                        Intent intent = new Intent(getActivity(), ContentActivity.class);
//                        建立一个bundle储存一些要传到activity的数据
                        Bundle bundle=new Bundle();
//                        传入影评请求是需要的id
                        bundle.putString("user_id",mData.get(position).getUser_id());

                        bundle.putInt("Id",mData.get(position).getReview_id());
                        bundle.putString("content",mData.get(position).getContent());
                        bundle.putString("UserName",mData.get(position).getName());
                        bundle.putString("reviewTitle",mData.get(position).getTitle());
                        bundle.putString("reviewTag",mData.get(position).getTag());
                        bundle.putString("reviewPicture",mData.get(position).getPicture());
                        bundle.putString("reviewTime",mData.get(position).getTime());
                        bundle.putString("reviewIcon",mData.get(position).getUser_picture());
                        bundle.putString("token",token);
//                        将bundle放进Intent
                        intent.putExtras(bundle);

//                        if(token!=null)
                            startActivity(intent);
//                        else Toast.makeText(getContext(),"请登陆，才能查看哦。",Toast.LENGTH_LONG).show();

//                这里设置点击事件
//                        Toast.makeText(getContext(), "你点击了第" + position + "个Item", Toast.LENGTH_SHORT).show();
                    }
                });

                if (response.isSuccessful()) {


                }
            }

            @Override
            public void onFailure(Call<List<groundsData>> call, Throwable t) {
                Log.e("TAG", "FAILURE");
                t.printStackTrace();
                refreshLayout.setRefreshing(false);
            }
        });


    }

}
