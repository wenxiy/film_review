package com.example.film_review.firstpage.review_content;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.film_review.R;
import com.example.film_review.firstpage.RetrofitAPI;
import com.example.film_review.firstpage.search.SearchText;
import com.example.film_review.firstpage.search.searchData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TagActivity extends AppCompatActivity {
    public String token;
    public String mTag;
    private RecyclerView list;
    private LinearLayoutManager mLinearLayoutManger;
    private List<searchData> searchList;
    private TagListViewAdapter mAdapter;
    private Button return_btn;
//    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        token = bundle.getString("token");
        mTag = bundle.getString("mTag");
        setContentView(R.layout.tag_activity);
        return_btn = findViewById(R.id.return_tag);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        找到控件
        list = findViewById(R.id.tag_recyclerview);
//        创建布局管理器
        mLinearLayoutManger = new LinearLayoutManager(getApplicationContext());
//        给recyclerview设置布局管理器
        list.setLayoutManager(mLinearLayoutManger);
//        构造适配器，给Recyclerview设置适配器
//        refreshLayout=findViewById(R.id.tag_refresh);
//        refreshLayout.setFocusable(false);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initData();
//                Log.d("TAG","下拉刷新");
//            }
//        });
        initData();

    }

    private void initData() {
        searchList = new ArrayList<>();
        SearchText searchText = new SearchText();
        searchText.setWords(mTag);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI API = retrofit.create(RetrofitAPI.class);
        Call<List<searchData>> task = API.search(searchText);
//        refreshLayout.setRefreshing(false);
        task.enqueue(new Callback<List<searchData>>() {
            @Override
            public void onResponse(Call<List<searchData>> call, Response<List<searchData>> response) {
                searchList.addAll(response.body());
                mAdapter = new TagListViewAdapter(searchList);
                list.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new TagListViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
//                        建立一个bundle储存一些要传到activity的数据
                        Bundle bundle = new Bundle();
//                        传入影评请求是需要的id
                        bundle.putInt("Id", searchList.get(position).getReview_id());
                        bundle.putString("content", searchList.get(position).getContent());
                        bundle.putString("UserName", searchList.get(position).getName());
                        bundle.putString("reviewTitle", searchList.get(position).getTitle());
                        bundle.putString("reviewTag", searchList.get(position).getTag());
                        bundle.putString("reviewPicture", searchList.get(position).getPicture());
                        bundle.putString("reviewTime", searchList.get(position).getTime());
                        bundle.putString("reviewIcon", searchList.get(position).getUser_picture());
                        bundle.putString("token", token);
//                        将bundle放进Intent
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<searchData>> call, Throwable t) {
//                refreshLayout.setRefreshing(false);
            }
        });
    }
}
