package com.example.film_review.personal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.film_review.R;
import com.example.film_review.firstpage.review_content.ContentActivity;
import com.example.film_review.personal.adapter.CollectListViewAdapter;
import com.example.film_review.personal.bean.CollectData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoriteFragment extends Fragment {
    private RecyclerView list;
    private LinearLayoutManager mLinearLayoutManger;
    private SwipeRefreshLayout refreshLayout;
    private String user_id;
    private String token;
    private CollectListViewAdapter mAdapter;
    private List<CollectData> mData;
    private Bundle bundle;
    private View view;

    public static FavoriteFragment newInstance(String token, String user_id) {

        Bundle args = new Bundle();
        args.putString("token", token);
        args.putString("user_id", user_id);
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        token = getArguments().getString("token");
        user_id = getArguments().getString("user_id");
        view = inflater.inflate(R.layout.fragment_personal_favorite, null);
        list = view.findViewById(R.id.personal_recyclerview);

        refreshLayout = view.findViewById(R.id.personal_refresh);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                Log.d("TAG", "下拉刷新");
            }
        });
        refreshLayout.setRefreshing(false);
        initData();
        return view;
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API mAPI = retrofit.create(API.class);
        Call<List<CollectData>> task = mAPI.getCollection(token, user_id);
        task.enqueue(new Callback<List<CollectData>>() {
            @Override
            public void onResponse(Call<List<CollectData>> call, Response<List<CollectData>> response) {
                Log.d("0", user_id + token);
                int code = response.code();
                mData = new ArrayList<>();
                mData.addAll(response.body());
//                Log.d("cpllect",response.body().get(0).getName()+" ");
                refreshLayout.setRefreshing(false);
                //        创建布局管理器
                mLinearLayoutManger = new LinearLayoutManager(getActivity());
//        给recyclerview设置布局管理器
                list.setLayoutManager(mLinearLayoutManger);
                mAdapter = new CollectListViewAdapter(mData);
                list.setAdapter(mAdapter);
//        构造适配器，给Recyclerview设置适配器
                Log.d("2", code + "");
//                Log.d("3", response.body().get(0) + " ");
                refreshLayout.setRefreshing(false);
                mAdapter.setOnItemClickListener(new CollectListViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                        将intent与目标activity联系
                        Intent intent = new Intent(getActivity(), ContentActivity.class);
//                        建立一个bundle储存一些要传到activity的数据
                        Bundle bundle = new Bundle();
//                        传入影评请求是需要的id
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
            }

            @Override
            public void onFailure(Call<List<CollectData>> call, Throwable t) {
                t.printStackTrace();
                refreshLayout.setRefreshing(false);
            }
        });
    }

}
