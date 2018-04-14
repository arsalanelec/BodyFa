package com.example.arsalan.mygym.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.arsalan.mygym.Objects.Trainer;
import com.example.arsalan.mygym.ProfileTrainerActivity;
import com.example.arsalan.mygym.R;
import com.example.arsalan.mygym.retrofit.ApiClient;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Arsalan on 10-02-2018.
 */

public class AdapterTrainers extends Adapter<AdapterTrainers.VH> {
    List<Trainer> trainerList;
    Activity mActivity;

    public AdapterTrainers(Activity activity, List<Trainer> trainerList) {
        this.trainerList = trainerList;
        this.mActivity = activity;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_trainer, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(VH h, int position) {
        final Trainer t = trainerList.get(position);
        h.thumbImg.setImageURI(ApiClient.BASE_URL+t.getThumbUrl());
        h.pointsTV.setText(String.valueOf(t.getPoint()));
        h.ratingBar.setRating(t.getRate());
        h.nameTV.setText(t.getName());
        h.honorTV.setText(t.getTitle());
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra(ProfileTrainerActivity.KEY_NAME, t.getName());
                i.putExtra(ProfileTrainerActivity.KEY_RATE, t.getRate());
                i.setClass(mActivity, ProfileTrainerActivity.class);
                mActivity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainerList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView thumbImg;
        TextView nameTV;
        TextView honorTV;
        TextView pointsTV;
        RatingBar ratingBar;


        public VH(View iv) {
            super(iv);
            nameTV=iv.findViewById(R.id.txtName);
            honorTV=iv.findViewById(R.id.txtTitle);
            ratingBar=iv.findViewById(R.id.ratingBar);
            pointsTV = iv.findViewById(R.id.txtPoints);
            thumbImg = iv.findViewById(R.id.imgThumb);

        }
    }
}
