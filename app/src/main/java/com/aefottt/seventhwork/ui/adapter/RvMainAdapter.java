package com.aefottt.seventhwork.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aefottt.seventhwork.R;
import com.aefottt.seventhwork.data.bean.MainArticle;

import java.util.ArrayList;

public class RvMainAdapter extends RecyclerView.Adapter<RvMainAdapter.ViewHolder>{

    private final ArrayList<MainArticle> mList;
    private OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        TextView title;
        TextView content;
        TextView time;
        TextView kind;
        ImageView userImg;
        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            userName = itemView.findViewById(R.id.tv_item_user_name);
            title = itemView.findViewById(R.id.tv_item_title);
            content = itemView.findViewById(R.id.tv_item_content);
            time = itemView.findViewById(R.id.tv_item_time);
            kind = itemView.findViewById(R.id.tv_item_article_kind);
            userImg = itemView.findViewById(R.id.iv_item_rv_user);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                        listener.onClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    public RvMainAdapter(ArrayList<MainArticle> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_rv, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainArticle article = mList.get(position);
        holder.userName.setText(article.getUserName());
        holder.title.setText(article.getTitle());
        holder.content.setText(article.getContent());
        holder.time.setText(article.getTime());
        holder.kind.setText(article.getKind());
        int random = (int) (Math.random()*3);
        if (random == 0) holder.userImg.setImageResource(R.mipmap.user_img1);
        else if (random == 1) holder.userImg.setImageResource(R.mipmap.user_img2);
        else if (random == 2) holder.userImg.setImageResource(R.mipmap.user_img3);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener{
        void onClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }
}
