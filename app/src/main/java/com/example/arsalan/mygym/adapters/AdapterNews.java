package com.example.arsalan.mygym.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arsalan.mygym.Objects.News;
import com.example.arsalan.mygym.R;
import com.example.arsalan.mygym.retrofit.ApiClient;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Arsalan on 10-02-2018.
 */

public class AdapterNews extends Adapter<AdapterNews.VH> {
    List<News> newsList;
Activity mActivity;
    public AdapterNews(Activity activity,List<News> newsList) {
        this.newsList = newsList;
        this.mActivity=activity;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(mActivity).inflate(R.layout.item_news,parent,false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(VH h, int position) {
        News news=newsList.get(position);
        h.titleTV.setText(news.getTitle());
        h.viewCntTV.setText(String.valueOf(news.getVisitcnt()));
        h.likeCntTV.setText(String.valueOf(news.getLikeCnt()));
        h.commentCntTV.setText(String.valueOf(news.getCommentCnt()));
        h.dateTV.setText(news.getDate());
        h.thumb.setImageURI(ApiClient.BASE_URL+news.getThumbUrl());


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView titleTV;
        TextView viewCntTV;
        TextView likeCntTV;
        TextView commentCntTV;
        TextView dateTV;
        SimpleDraweeView thumb;
        public VH(View itemView) {
            super(itemView);
           titleTV= itemView.findViewById(R.id.txtAddress);
           viewCntTV=itemView.findViewById(R.id.txtViewCnt);
           likeCntTV=itemView.findViewById(R.id.txtLikeCnt);
           commentCntTV=itemView.findViewById(R.id.txtCommentCnt);
           dateTV=itemView.findViewById(R.id.txtDate);
           thumb=itemView.findViewById(R.id.imgThumb);
        }
    }
}
