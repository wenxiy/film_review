package com.example.film_review.firstpage.review_content;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film_review.R;
import com.example.film_review.firstpage.RetrofitAPI;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.EmptyStackException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class ListViewAdapterReview extends RecyclerView.Adapter<ListViewAdapterReview.InnerHolder> {
    private String token;
    private List<ReviewContext.CommentBean> Data;
//    private boolean comment_like;

    public ListViewAdapterReview(List<ReviewContext.CommentBean> data,String token) {
        this.token=token;
        this.Data=data;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.comment_item,parent,false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.setData(Data.get(position));

    }

    @Override
    public int getItemCount() {
        if(Data==null)
            return 0;
        else return Data.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView mCommentIcon;
        private TextView mCommentTime;
        private TextView mCommentName;
        private TextView mCommentContent;
        private Button mCommentLike;
        private String comment_id;
        private boolean comment_like;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mCommentIcon=itemView.findViewById(R.id.Comment_icon);
            mCommentTime=itemView.findViewById(R.id.Comment_Time);
            mCommentName=itemView.findViewById(R.id.Comment_name);
            mCommentContent=itemView.findViewById(R.id.Comment_Content);
            mCommentLike=itemView.findViewById(R.id.liking_btn);

        }


        public void setData(ReviewContext.CommentBean commentBean) {
            mCommentIcon.setImageURI(commentBean.getUser_picture());
            mCommentTime.setText(commentBean.getTime());
            mCommentName.setText(commentBean.getName());
            mCommentContent.setText(commentBean.getContent());

            comment_id=String.valueOf(commentBean.getComment_id());
            comment_like=commentBean.isComment_like();
            Log.d("T","isComment_like:"+comment_like);
            mCommentLike.setSelected(comment_like);
            mCommentLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(comment_like==false) {
                        mCommentLike.setSelected(true);
                        Log.d("TAG","qingqiu:"+comment_id+token);
                        comment_like=true;
                        commentlikable();

//                        Toast.makeText(getAdapterPosition(),"点赞成功！",Toast.LENGTH_SHORT).show();
                    }
                    else if(comment_like==true){
                        mCommentLike.setSelected(false);
                        comment_like=false;
                        commentlikable();
//                        Toast.makeText(getContext().,"你已取消点赞！",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Log.d("TAG","lking"+comment_like);
        }

    private void commentlikable() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://114.215.201.204:9091")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI API=retrofit.create(RetrofitAPI.class);
        Call<EmptyStackException> task=API.CommentLiking(token,comment_id);
        Log.d("TAG","commentlike");
        task.enqueue(new Callback<EmptyStackException>() {
            @Override
            public void onResponse(Call<EmptyStackException> call, Response<EmptyStackException> response) {
                int code=response.code();
                Log.d("TAG","commentlike"+code);

            }

            @Override
            public void onFailure(Call<EmptyStackException> call, Throwable t) {

            }
        });
    }
}
}
