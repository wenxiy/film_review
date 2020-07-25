package com.example.film_review.firstpage.review_content;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film_review.R;
import com.example.film_review.firstpage.attention.ListViewAdapter;
import com.example.film_review.firstpage.search.searchData;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

class TagListViewAdapter extends RecyclerView.Adapter<TagListViewAdapter.InnerHolder> {

    private final List<searchData> Data;
    private OnItemClickListener mOnItemClickListener;

    public TagListViewAdapter(List<searchData> list){
        this.Data=list;
    }

    @NonNull
    @Override
    public TagListViewAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.tag_item,parent,false);
        return new InnerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TagListViewAdapter.InnerHolder holder, int position) {
        if(Data!=null) holder.setData(Data.get(position));
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        //设置一个监听器，其实就是设置一个回调的接口
        this.mOnItemClickListener=listener;

    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }


    @Override
    public int getItemCount() {
        if(Data!=null)
            return Data.size();
        else
        return 10;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        private final SimpleDraweeView mIcon;
        private final SimpleDraweeView mPicture;
        private TextView mContent;
        private TextView mTitle;
        private TextView mName;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mName=itemView.findViewById(R.id.tag_name);
            mTitle=itemView.findViewById(R.id.tag_Title);
            mIcon=itemView.findViewById(R.id.tag_icon);
            mContent=itemView.findViewById(R.id.tag_content);
            mPicture=itemView.findViewById(R.id.tag_IconId);

        }

        public void setData(searchData searchData) {
            mName.setText(searchData.getName());
            mTitle.setText(searchData.getTitle());
            mIcon.setImageURI(searchData.getUser_picture());
            mContent.setText(searchData.getContent());
            mPicture.setImageURI(searchData.getPicture());
        }
    }
}
