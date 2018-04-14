package com.example.arsalan.mygym;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;

public class ProfileTrainerActivity extends AppCompatActivity {
static public final String KEY_NAME="key name";
    static public final String KEY_RATE="key rate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_trainer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nameTV = findViewById(R.id.txtName);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        if(getIntent().getExtras()!=null){
            nameTV.setText(""+getIntent().getStringExtra(KEY_NAME));
            ratingBar.setRating((float) getIntent().getDoubleExtra(KEY_RATE,5));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
