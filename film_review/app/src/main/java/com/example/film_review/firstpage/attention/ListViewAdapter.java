package com.example.film_review.firstpage.attention;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film_review.R;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.InnerHolder> {

    //设置数据

    private List<AttentionItemData> mData;
    private OnItemClickListener mOnItemClickListener;


    public ListViewAdapter(List<AttentionItemData> data) {
        this.mData = data;
    }


    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //传进去的view就是条目界面
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.attention_item, parent, false);


        return new InnerHolder(view);
    }

    @Override

    //用于绑定holder，一般用来设置数据
    //返回点击后的对应的Viewholder中控制的item
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {

        if (mData != null) holder.setData(mData.get(position));
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }

    }

    @Override
    //返回条目个数
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        //设置一个监听器，其实就是设置一个回调的接口
        this.mOnItemClickListener = listener;

    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    //这个方法用于创建条目的view
    public class InnerHolder extends RecyclerView.ViewHolder {
        private Button mtag;
        private TextView mReviewName;
        private TextView mContent;
        private TextView mTime;
        private TextView mName;
        private ImageView mIcon;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);


            mName = (TextView) itemView.findViewById(R.id.attention_userName);
            mTime = itemView.findViewById(R.id.attention_userTime);
            mReviewName = itemView.findViewById(R.id.attention_reviewName);
            mContent = itemView.findViewById(R.id.attention_reviewContent);
            mtag = itemView.findViewById(R.id.attention_tag);
        }

        public void setData(AttentionItemData data) {
            mName.setText(data.getName());
            mTime.setText(data.getTime());
            mReviewName.setText(data.getTitle());
            mContent.setText(data.getContent());
            mtag.setText(data.getTag());

        }
    }
}