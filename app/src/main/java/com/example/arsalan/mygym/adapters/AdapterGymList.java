package com.example.arsalan.mygym.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arsalan.mygym.Objects.Gym;
import com.example.arsalan.mygym.ProfileGymActivity;
import com.example.arsalan.mygym.R;

import java.util.List;

/**
 * Created by Arsalan on 10-02-2018.
 */

public class AdapterGymList extends Adapter<AdapterGymList.VH> {
    List<Gym> gymList;
    Activity mActivity;

    public AdapterGymList(Activity activity, List<Gym> gymList) {
        this.gymList = gymList;
        this.mActivity = activity;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_gym, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setClass(mActivity, ProfileGymActivity.class);
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
        return gymList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);
        }
    }
}
