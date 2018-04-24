package com.example.arsalan.mygym;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arsalan.mygym.Objects.RetroResult;
import com.example.arsalan.mygym.retrofit.ApiClient;
import com.example.arsalan.mygym.retrofit.ApiInterface;
import com.facebook.drawee.view.SimpleDraweeView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostContentActivity extends AppCompatActivity {

    private final String TAG = "PostContentActivity";
    private Uri resultUri;
    private SimpleDraweeView image;
    private Spinner cateSpn;
    private EditText contentTxt;
    private Context mContext;

    public PostContentActivity() {
        mContext = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cateSpn = (Spinner) findViewById(R.id.spnCategory);
        contentTxt = findViewById(R.id.etContent);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MultipartBody.Part is used to send also the actual file name
                if (resultUri == null) {
                    Toast.makeText(mContext, "لطفا یک عکس انتخاب کنید", Toast.LENGTH_LONG).show();
                    return;
                }
                if (contentTxt.getText().toString().length() < 2) {

                    contentTxt.setError("نوشته کوتاه است");
                    return;
                }
                File imageFile = new File(resultUri.getPath());
                // create RequestBody instance from file
                Log.d(TAG, "onClick: mediatype:" + MimeTypeMap.getFileExtensionFromUrl(resultUri.getPath()));
                final RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse("image/*"),//MimeTypeMap.getFileExtensionFromUrl(resultUri.getPath())),
                                imageFile);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("picture", imageFile.getName(), requestFile);
                final ProgressDialog waitingDialog = new ProgressDialog(PostContentActivity.this);
                waitingDialog.setMessage("درحال ارسال مطلب\nلظفا چند لحظه منتظر بمانبد...");
                waitingDialog.show();

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<RetroResult> call = apiService.sendContent("Bearer " + ((MyApplication) getApplication()).getCurrentToken().getToken()
                        , ((MyApplication) getApplication()).getCurrentUser().getId()
                        , ((StringWithTag) cateSpn.getSelectedItem()).tag, contentTxt.getText().toString()
                        , body);
                call.enqueue(new Callback<RetroResult>() {
                    @Override
                    public void onResponse(Call<RetroResult> call, Response<RetroResult> response) {
                        waitingDialog.dismiss();
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: respone:" + response.body().getResult());
                            Toast.makeText(mContext, "مطلب شما با موفقیت ارسال گردید و پس از تایید منتشر خواهد شد.", Toast.LENGTH_LONG).show();
                            PostContentActivity.super.onBackPressed();
                        } else {
                            Log.d(TAG, "onResponse: is not ok:" + response.message());
                            Toast.makeText(mContext, "خظایی پیش آمده لطفا مجددا تلاش کنید", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RetroResult> call, Throwable t) {
                        waitingDialog.dismiss();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        Toast.makeText(mContext, "خظایی پیش آمده لطفا مجددا تلاش کنید", Toast.LENGTH_LONG).show();
                    }
                });

            }


        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Create your ArrayList collection using StringWithTag instead of String. */
        List<StringWithTag> itemList = new ArrayList<StringWithTag>();

        /* Iterate through your original collection, in this case defined with an Integer key and String value. */


        /* Build the StringWithTag List using these keys and values. */
        itemList.add(new StringWithTag("اخبار ورزشی", 1));
        itemList.add(new StringWithTag("اخبار تغذیه", 2));



        /* Set your ArrayAdapter with the StringWithTag, and when each entry is shown in the Spinner, .toString() is called. */
        ArrayAdapter<StringWithTag> spinnerAdapter = new ArrayAdapter<StringWithTag>(this, android.R.layout.simple_spinner_item, itemList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cateSpn.setAdapter(spinnerAdapter);
        image = findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setActivityTitle("انتخاب عکس مطلب")
                        .setAllowFlipping(false)
                        .setAllowRotation(true)
                        .setAspectRatio(1, 1)
                        .setFixAspectRatio(true)
                        .setRequestedSize(600, 600)
                        .start(PostContentActivity.this);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Log.d("EDITPROFILEACTIVITY", "onActivityResult: " + resultUri);
                image.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private static class StringWithTag {
        public String string;
        public int tag;

        public StringWithTag(String string, Integer tag) {
            this.string = string;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return string;
        }
    }
}
