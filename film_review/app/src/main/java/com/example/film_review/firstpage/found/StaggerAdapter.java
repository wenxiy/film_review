package com.example.film_review.firstpage.found;

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
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


public class StaggerAdapter extends RecyclerView.Adapter<StaggerAdapter.InnerHolder> {


    private List<groundsData> mData;
    private OnItemClickListener mOnItemClickListener;
    //    设置数据
    public StaggerAdapter(List<groundsData> data){
        this.mData=data;
    }



    @NonNull
    @Override

    public StaggerAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //    传进去的view是每个item的界面
        LayoutInflater mLayoutInflater=LayoutInflater.from(parent.getContext());
        View view=mLayoutInflater.inflate(R.layout.found_item,parent,false);
        return  new InnerHolder(view);
    }

    @Override
//    用于绑定holdder。一般用来设置数据
    public void onBindViewHolder(@NonNull StaggerAdapter.InnerHolder holder, final int position) {

        holder.setData(mData.get(position));

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
//    返回条目个数
    public int getItemCount() {
        if(mData!=null){
            return  mData.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        public int ReviewId;

        private SimpleDraweeView mBodyPicture;
        private SimpleDraweeView mUserIcon;
        private TextView mFoundItemContent;
        private TextView mFoundItemTag;
        private TextView mFoundItemTitle;
        private TextView mFoundItemName;
        private TextView mFoundItemTime;
        private Button mLike;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
             mFoundItemName=itemView.findViewById(R.id.foundItemName);
             mFoundItemTitle=itemView.findViewById(R.id.foundItemTitle);
             mFoundItemTag=itemView.findViewById(R.id.foundItemTag);
             mFoundItemContent=itemView.findViewById(R.id.foundItemContent);
             mFoundItemTime=itemView.findViewById(R.id.foundItemTime);
             mBodyPicture=itemView.findViewById(R.id.body);
             mUserIcon=itemView.findViewById(R.id.icons2);

        }

        public  void setData(groundsData aData) {
            mFoundItemName.setText(aData.getName());
            mFoundItemContent.setText(aData.getContent());
            mFoundItemTag.setText(aData.getTag());
            mFoundItemTitle.setText(aData.getTitle());
            mFoundItemTime.setText(aData.getTime());
            mBodyPicture.setImageURI(aData.getPicture());
            mUserIcon.setImageURI(aData.getUser_picture());
            ReviewId=aData.getReview_id();

        }
    }
}
