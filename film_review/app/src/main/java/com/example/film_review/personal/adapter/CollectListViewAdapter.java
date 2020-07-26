package com.example.film_review.personal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film_review.R;
import com.example.film_review.personal.bean.CollectData;

import java.util.List;

public class CollectListViewAdapter extends RecyclerView.Adapter<CollectListViewAdapter.InnerHolder> {
    public List<CollectData> mData;
    private View view;
    private OnItemClickListener mOnItemClickListener;

    public CollectListViewAdapter(List<CollectData> data) {
        this.mData=data;
    }

    @NonNull
    @Override
    public CollectListViewAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        view=layoutInflater.inflate(R.layout.collect_item,parent,false);


        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectListViewAdapter.InnerHolder holder, int position) {
        if(mData!=null) holder.setData(mData.get(position));
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mData!=null)
            return mData.size();
        else
        return 0;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        //设置一个监听器，其实就是设置一个回调的接口
        this.mOnItemClickListener=listener;

    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        private Button mtag;
        private TextView mReviewName;
        private TextView mContent;
        private TextView mTime;
        private TextView mName;
        private ImageView mIcon;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mName=(TextView)itemView.findViewById(R.id.collect_userName);
            mTime=itemView.findViewById(R.id.collect_Time);
            mReviewName=itemView.findViewById(R.id.collect_reviewName);
            mContent=itemView.findViewById(R.id.collect_reviewContent);
            mtag=itemView.findViewById(R.id.collect_tag);
        }

        public void setData(CollectData collectData) {
            mName.setText(collectData.getName());
            mTime.setText(collectData.getTime());
            mReviewName.setText(collectData.getTitle());
            mContent.setText(collectData.getContent());
            mtag.setText(collectData.getTag());
        }
    }
}
