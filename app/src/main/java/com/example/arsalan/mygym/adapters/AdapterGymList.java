package com.example.arsalan.mygym.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.arsalan.mygym.Objects.Gym;
import com.example.arsalan.mygym.ProfileGymActivity;
import com.example.arsalan.mygym.R;
import com.example.arsalan.mygym.retrofit.ApiClient;
import com.facebook.drawee.view.SimpleDraweeView;

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
    public void onBindViewHolder(VH h, int position) {
        Gym g = gymList.get(position);
        h.ratingBar.setRating((float) g.getRate());
        h.pointsTV.setText(String.valueOf(g.getPoint()));
        h.addressTV.setText(g.getAddress());
        h.gymNameTV.setText(g.getName());
        h.thumbImg.setImageURI(ApiClient.BASE_URL+g.getPictureUrl());
        Log.d("AdapterGymList", "onBindViewHolder: rate:"+g.getRate());

    }

    @Override
    public int getItemCount() {
        return gymList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView thumbImg;
        TextView gymNameTV;
        TextView addressTV;
        TextView pointsTV;
        RatingBar ratingBar;

        public VH(View iv) {
            super(iv);
            thumbImg = iv.findViewById(R.id.imgThumb);
            gymNameTV = iv.findViewById(R.id.txtName);
            addressTV = iv.findViewById(R.id.txtTitle);
            pointsTV = iv.findViewById(R.id.txtPoints);
            ratingBar = iv.findViewById(R.id.ratingBar);
        }
    }
}
