package com.example.film_review.personal;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.film_review.MainActivity;
import com.example.film_review.R;
import com.example.film_review.firstpage.found.StaggerAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import androidx.recyclerview.widget.RecyclerView;


public class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.InnerHolder> {
    public List<getmyData> mdata;
    private OnItemClickListener  mOnItemClickListener;
    public MyRecyclerviewAdapter(List<getmyData> data)
    {
        this.mdata=data;
    }

    @NonNull
    @Override
    public MyRecyclerviewAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
       View view=layoutInflater.inflate(R.layout.my_item,parent,false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerviewAdapter.InnerHolder holder, int position) {
    //   holder.setdata(mdata.get(position));
       if(mOnItemClickListener!=null)
       {
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mOnItemClickListener.onItemClick(position);
               }
           });
       }

    }
    public void setOnItemClickListener(StaggerAdapter.OnItemClickListener listener) {
        //设置一个监听器，其实就是设置一个回调的接口
        this.mOnItemClickListener= (OnItemClickListener) listener;

    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder{


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
