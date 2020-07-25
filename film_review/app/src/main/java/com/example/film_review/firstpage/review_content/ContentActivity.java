package com.example.film_review.firstpage.review_content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film_review.R;
import com.example.film_review.firstpage.RetrofitAPI;
import com.example.film_review.firstpage.search.SearchText;
import com.example.film_review.firstpage.search.searchData;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

import static java.security.AccessController.getContext;

public class ContentActivity extends AppCompatActivity {

    private TextView Context;
    private TextView UserName;
    private SimpleDraweeView icon;
    private TextView time;
    private SimpleDraweeView ReviewPicture;
    private TextView ReviewTitle;
    private TextView Content;
    private Button ReviewTag;
    private String token;
    private int ReviewId;
    private List<ReviewContext.CommentBean> commentList;
    private LinearLayoutManager mLinearLayoutManager;
    private List<ReviewContext.CommentBean> Data;
    private ListViewAdapterReview mAdapter;
    private String review_id;
    private RecyclerView list;
    private EditText CommentEdit;
    private Button CommentSend;
    private String EditContent;
    private SingleComment mSingleComment;
    private SingleCommentData singleCommentData;
    private InputMethodManager mInputMethodManager;
    private Button btn_liking;
    private Button btn_collect;
    private boolean review_like;
    private boolean review_collection;
    private Button btn_comment;
    private Button btn_tag;
    private List<searchData> searchList;
    private String mTag;
    private Button btn_attention;
    private boolean isAttention;
    private String user_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        Fresco.initialize(this);
//        取出传进取的bundle
        Bundle bundle=this.getIntent().getExtras();
//        通过传入时设定的键值取出
        ReviewId=bundle.getInt("Id");


//        测试是否传入了review的id
        review_id=String.valueOf(ReviewId);
        Log.d("Id",review_id);

        token=bundle.getString("token");
        Log.d("Token","youkezhuangtai"+token);

        user_id=bundle.getString("user_id");

        setContentView(R.layout.content_review);

        list=this.findViewById(R.id.Comment_ListView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLinearLayoutManager);

        btn_comment=findViewById(R.id.btn_ReviewComment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"我只是个摆设，你点我也没用-_—",Toast.LENGTH_SHORT);
            }
        });
        btn_collect=findViewById(R.id.btn_ReviewCollect);
        btn_liking=findViewById(R.id.btn_ReviewLiking);
        btn_attention=findViewById(R.id.attention_btn);
        initData();
//        对操作绑定控件
        if(token==null) {
            isAttention=false;
            btn_attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"登录以后才可以关注别人！！！",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            getAttentionData();
//        btn_attention.setSelected(isAttention);
            btn_attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAttention == false) {
                        btn_attention.setSelected(true);
                        TakeAttention();
                        isAttention = true;
                        Toast.makeText(getApplicationContext(), "关注成功！", Toast.LENGTH_SHORT).show();
                    } else if (isAttention == true) {
                        btn_attention.setSelected(false);
                        TakeAttention();
                        isAttention = false;
                        Toast.makeText(getApplicationContext(), "取消关注！", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        btn_liking.setSelected(review_like);
        Log.d("L","likie"+review_like);
        btn_liking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(review_like==false) {
                    btn_liking.setSelected(true);
                    likable();
                    review_like=true;
                    Toast.makeText(getApplicationContext(),"点赞成功！",Toast.LENGTH_SHORT).show();
                }
                else if(review_like==true){
                    btn_liking.setSelected(false);
                    likable();
                    review_like=false;
                    Toast.makeText(getApplicationContext(),"你已取消点赞！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(review_collection==false) {
                    btn_collect.setSelected(true);
                    collectable();
                    review_collection=true;
                    Toast.makeText(getApplicationContext(),"收藏成功！",Toast.LENGTH_SHORT).show();
                }
                else if(review_collection==true){
                    btn_collect.setSelected(false);
                    collectable();
                    review_collection=false;
                    Toast.makeText(getApplicationContext(),"你已取消收藏！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Context=findViewById(R.id.context_content);
        Context.setMovementMethod(ScrollingMovementMethod.getInstance());

        UserName=findViewById(R.id.Context_UserName);
        UserName.setText(bundle.getString("UserName"));

        icon=findViewById(R.id.context_icon);
        icon.setImageURI(bundle.getString("reviewIcon"));

        time=findViewById(R.id.Context_commitTime);
        time.setText(bundle.getString("reviewTime"));

        ReviewPicture=findViewById(R.id.context_Picture);
        ReviewPicture.setImageURI(bundle.getString("reviewPicture"));

        ReviewTitle=findViewById(R.id.contentTitle);
        ReviewTitle.setText(bundle.getString("reviewTitle"));

        Content=findViewById(R.id.context_content);
        Context.setText(bundle.getString("content"));

        ReviewTag=findViewById(R.id.Review_Tag);
        ReviewTag.setText(bundle.getString("reviewTag"));
        ReviewTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                将tag的值传到activity里，便于之后用于搜索做网络请求获得list
                mTag=ReviewTag.getText().toString();
                Log.d("0","mTag"+mTag);
                Intent intent=new Intent(getApplicationContext(),TagActivity.class);
                Bundle bundle2=new Bundle();
                bundle2.putString("token",token);
                bundle2.putString("mTag",mTag);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });

        CommentEdit=findViewById(R.id.Comment_Edit);
        CommentEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                设置输入框可聚焦commentBean
                CommentEdit.setFocusable(true);
//                设置🉑聚焦
                CommentEdit.setFocusableInTouchMode(true);
//                请求焦点
                CommentEdit.requestFocus();
//                获取焦点
                CommentEdit.findFocus();
//                显示输入框
                mInputMethodManager.showSoftInput(CommentEdit,InputMethodManager.SHOW_FORCED);
            }
        });

//        s实例化了界面的软键盘
        mInputMethodManager=(InputMethodManager)getSystemService(android.content.Context.INPUT_METHOD_SERVICE);

        CommentSend=findViewById(R.id.Send_Commment);
        CommentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditContent=CommentEdit.getText().toString();
                if(token==null) Toast.makeText(getApplicationContext(),"游客状态不能发评论！",Toast.LENGTH_SHORT).show();
                else if(EditContent.equals("")){
                    Toast.makeText(getApplicationContext(),"输入的内容不能为空哦！",Toast.LENGTH_SHORT).show();
                }
                else {
                    mSingleComment=new SingleComment();
                    mSingleComment.setContent(EditContent);
                    Log.d("tag",mSingleComment.getContent()+"");
                    send();
//                    设置输入框不可聚焦，即失去焦点
                    CommentEdit.setFocusable(false);
//                    把键盘给隐藏
                    if(mInputMethodManager.isActive()){
                        mInputMethodManager.hideSoftInputFromWindow(CommentEdit.getWindowToken(),0);
                    }
                    CommentEdit.setText(null);
                }
            }
        });

    }

    private void TakeAttention() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI API=retrofit.create(RetrofitAPI.class);
        Call<EmptyStackException> task=API.takeAttention(token,user_id);
        task.enqueue(new Callback<EmptyStackException>() {
            @Override
            public void onResponse(Call<EmptyStackException> call, Response<EmptyStackException> response) {
                int code=response.code();
                Log.d("sendAttention",code+" ");
            }

            @Override
            public void onFailure(Call<EmptyStackException> call, Throwable t) {

            }
        });
    }

    private void getAttentionData() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI API=retrofit.create(RetrofitAPI.class);
        Call<UserData.UserInfoBean> task=API.AttentionData(token,user_id);
        Log.d("111",user_id+" ");
        task.enqueue(new Callback<UserData.UserInfoBean>() {
            @Override
            public void onResponse(Call<UserData.UserInfoBean> call, Response<UserData.UserInfoBean> response) {
                int code=response.code();
                Log.d("00",code+"");
                isAttention=response.body().isAttention();
                btn_attention.setSelected(isAttention);
                Log.d("000",response.body().isAttention()+"");
            }

            @Override
            public void onFailure(Call<UserData.UserInfoBean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void collectable() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI API=retrofit.create(RetrofitAPI.class);
        Call<EmptyStackException> task=API.Collecting(token,review_id);
        Log.d("TAG","like");
        task.enqueue(new Callback<EmptyStackException>() {
            @Override
            public void onResponse(Call<EmptyStackException> call, Response<EmptyStackException> response) {

            }

            @Override
            public void onFailure(Call<EmptyStackException> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void likable() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI API=retrofit.create(RetrofitAPI.class);
        Call<EmptyStackException> task=API.Liking(token,review_id);
        Log.d("TAG","like");
        task.enqueue(new Callback<EmptyStackException>() {
            @Override
            public void onResponse(Call<EmptyStackException> call, Response<EmptyStackException> response) {
//                review_like=true;
//                Toast.makeText(getApplicationContext(),"点赞成功！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<EmptyStackException> call, Throwable t) {

            }
        });
    }


    private void send() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI API=retrofit.create(RetrofitAPI.class);
        Call<SingleCommentData> task=API.SentComment(token,review_id,mSingleComment);
        Log.d("1","1");
        task.enqueue(new Callback<SingleCommentData>() {
            @Override
            public void onResponse(Call<SingleCommentData> call, Response<SingleCommentData> response) {
                singleCommentData=new SingleCommentData();
                singleCommentData=response.body();
                Log.d("TAG","信息："+singleCommentData.getTime());
                Toast.makeText(getApplicationContext(),"发布评论成功",Toast.LENGTH_SHORT).show();
                initData();
            }

            @Override
            public void onFailure(Call<SingleCommentData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initData() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI Api=retrofit.create(RetrofitAPI.class);
        Call<ReviewContext> task=Api.getReview(token,review_id);
        task.enqueue(new Callback<ReviewContext>() {
            @Override
            public void onResponse(Call<ReviewContext> call, Response<ReviewContext> response) {
                int code=response.code();
                ReviewContext mReviewContext=response.body();
                Data= new ArrayList<>();
                Data.addAll(mReviewContext.getComment());
                mAdapter=new ListViewAdapterReview(Data,token);
                list.setAdapter(mAdapter);

                Log.d("TAG","code"+code);
                Log.d("body","resopnse-->"+response.body().getComment());
                review_like=response.body().isReview_like();
                review_collection=response.body().isReview_collection();
                Log.d("LIKING","like"+review_like);
                btn_liking.setSelected(review_like);
                btn_collect.setSelected(review_collection);
            }

            @Override
            public void onFailure(Call<ReviewContext> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}