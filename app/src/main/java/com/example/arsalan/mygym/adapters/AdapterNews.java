package com.example.arsalan.mygym.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arsalan.mygym.Objects.News;
import com.example.arsalan.mygym.R;

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
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);
        }
    }
}
