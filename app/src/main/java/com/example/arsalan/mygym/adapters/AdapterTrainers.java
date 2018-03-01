package com.example.arsalan.mygym.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arsalan.mygym.Objects.Trainer;
import com.example.arsalan.mygym.ProfileTrainedActivity;
import com.example.arsalan.mygym.R;

import java.util.List;

/**
 * Created by Arsalan on 10-02-2018.
 */

public class AdapterTrainers extends Adapter<AdapterTrainers.VH> {
    List<Trainer> trainerList;
Activity mActivity;
    public AdapterTrainers(Activity activity, List<Trainer> trainerList) {
        this.trainerList = trainerList;
        this.mActivity=activity;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(mActivity).inflate(R.layout.item_trainer,parent,false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setClass(mActivity, ProfileTrainedActivity.class);
                mActivity.startActivity(i);
            }
        });
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return trainerList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);
        }
    }
}
