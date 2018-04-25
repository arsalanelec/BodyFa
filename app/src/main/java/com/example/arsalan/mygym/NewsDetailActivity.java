package com.example.arsalan.mygym;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arsalan.mygym.Objects.Comment;
import com.example.arsalan.mygym.Objects.News;
import com.example.arsalan.mygym.Objects.RetCommentList;
import com.example.arsalan.mygym.Objects.RetNewsDetail;
import com.example.arsalan.mygym.Objects.RetroResult;
import com.example.arsalan.mygym.adapters.AdapterComments;
import com.example.arsalan.mygym.retrofit.ApiClient;
import com.example.arsalan.mygym.retrofit.ApiInterface;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends AppCompatActivity {
    public static final String KEY_NEWS_ID = "new id key";
    private static final String TAG = "NewsDetailActivity" ;
    private SimpleDraweeView image;
    private TextView titleTV;
    private TextView contentTV;
    private TextView commentsCountTV;
    private TextView sendCommentTV;

    private AdapterComments adapter;
    private RecyclerView commentRV;
    private List<Comment> commentList;
    private FloatingActionButton sendCommentBtn;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleTV = findViewById(R.id.txtTitle);
        contentTV = findViewById(R.id.txtContent);
        image = findViewById(R.id.image);
        commentsCountTV = findViewById(R.id.txtCommentsTitle);
        final long newsId = getIntent().getLongExtra(KEY_NEWS_ID, 1);
        getNewsDetaiWeb(newsId);

        commentList = new ArrayList<>();

        adapter = new AdapterComments(commentList);
        commentRV = findViewById(R.id.rcyComments);
        commentRV.setLayoutManager(new LinearLayoutManager(this));
        commentRV.setAdapter(adapter);

        final AlertDialog sendCommentDialog = new AlertDialog.Builder(NewsDetailActivity.this, R.style.AlertDialogCustom)

                .setNeutralButton("ارسال", null)
                .setCustomTitle(LayoutInflater.from(NewsDetailActivity.this).inflate(R.layout.title_send_comment, null, false))
                .setView(LayoutInflater.from(NewsDetailActivity.this).inflate(R.layout.simple_edit_text, null, false))
                .create();

        sendCommentDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                sendCommentTV = sendCommentDialog.findViewById(R.id.editText);
                sendCommentDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (sendCommentTV.length() < 2) {
                            sendCommentTV.setError("نوشته خیلی کوتاه است.");
                            return;
                        }
                        sendCommentWeb(newsId, "" + sendCommentTV.getText().toString());
                        sendCommentDialog.dismiss();
                    }
                });
            }
        });
        sendCommentBtn = findViewById(R.id.fabSendComment);
        sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sendCommentDialog.show();

            }
        });
    }

    private void getNewsDetaiWeb(final long newId) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog waitingDialog = new ProgressDialog(this);
        waitingDialog.setMessage("لظفا چند لحظه منتظر بمانبد...");
        waitingDialog.show();
        Call<RetNewsDetail> call = apiService.getNewsDetail(newId);
        call.enqueue(new Callback<RetNewsDetail>() {
            @Override
            public void onResponse(Call<RetNewsDetail> call, Response<RetNewsDetail> response) {
                waitingDialog.dismiss();
                if (response.isSuccessful())
                    Log.d("getNewsWeb", "onResponse: record:" + response.body().getRecord().getTitle() + " newsId:" + response.body().getRecord().getId());
                News news = response.body().getRecord();
                titleTV.setText(news.getTitle());
                contentTV.setText(news.getDesc());
                image.setImageURI(ApiClient.BASE_URL + news.getPictureUrl());
                Log.d(TAG, "onResponse: pictureUrl:"+news.getPictureUrl());
                commentsCountTV.setText(getString(R.string.userComments, news.getCommentCnt()));
                getCommentWeb(news.getId());
            }

            @Override
            public void onFailure(Call<RetNewsDetail> call, Throwable t) {
                waitingDialog.dismiss();

            }
        });
    }

    private void getCommentWeb(final long newId) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog waitingDialog = new ProgressDialog(this);
        waitingDialog.setMessage("لظفا چند لحظه منتظر بمانبد...");
        waitingDialog.show();
        Call<RetCommentList> call = apiService.getCommentList("Bearer " + ((MyApplication) getApplication()).getCurrentToken().getToken(), 0, 100, newId);
        call.enqueue(new Callback<RetCommentList>() {
            @Override
            public void onResponse(Call<RetCommentList> call, Response<RetCommentList> response) {
                waitingDialog.dismiss();
                if (response.isSuccessful() && response.body() != null)
                    Log.d("getNewsWeb", "onResponse: record:" + response.body().getRecords().size());
                commentList.removeAll(commentList);
                if(response.body()!=null&&response.body().getRecords().size()>0)
                commentList.addAll(response.body().getRecords());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RetCommentList> call, Throwable t) {
                waitingDialog.dismiss();
            }
        });
    }

    private void sendCommentWeb(final long newId, String comment) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final ProgressDialog waitingDialog = new ProgressDialog(this);
        waitingDialog.setMessage("لظفا چند لحظه منتظر بمانبد...");
        waitingDialog.show();
        Call<RetroResult> call = apiService.sendNewsComment("Bearer " + ((MyApplication) getApplication()).getCurrentToken().getToken(), ((MyApplication) getApplication()).getCurrentUser().getId(), newId, comment);
        call.enqueue(new Callback<RetroResult>() {
            @Override
            public void onResponse(Call<RetroResult> call, Response<RetroResult> response) {
                waitingDialog.dismiss();
                if (response.isSuccessful() && response.body() != null)
                    Log.d("sendCommentWeb", "onResponse: record:" + response.body().getResult());
                if (response.body().getResult().equals("OK")) {
                    Snackbar.make(sendCommentBtn, "نظر شما ارسال شد و به محض تایید منتشر خواهد شد.", Snackbar.LENGTH_LONG)
                            .setAction("متوجه شدم", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(Color.YELLOW).show();
                } else {
                    Toast.makeText(getApplicationContext(), "مشکلی رخ داده است لطفا مجددا تلاش کنید.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RetroResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "مشکلی رخ داده است لطفا مجددا تلاش کنید.", Toast.LENGTH_LONG).show();

            }
        });
    }
}
