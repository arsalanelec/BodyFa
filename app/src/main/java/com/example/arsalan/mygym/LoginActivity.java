package com.example.arsalan.mygym;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.arsalan.mygym.Objects.RetUserProfile;
import com.example.arsalan.mygym.Objects.Token;
import com.example.arsalan.mygym.fragments.LoginFragment;
import com.example.arsalan.mygym.fragments.RegisterFragment;
import com.example.arsalan.mygym.retrofit.ApiClient;
import com.example.arsalan.mygym.retrofit.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements
        LoginFragment.OnFragmentInteractionListener
        , RegisterFragment.OnFragmentInteractionListener {


    private final String KEY_USERNAME = "myGymUserNameKey";
    private final String KEY_PASSWORD = "myGymPassword";

    private static final int ACTIVITY_REGISTRATION1 = 0;
    private static final int ACTIVITY_SENDALERT3 = 1;
    private static final int ACTIVITY_REGISTRATION2 = 2;

    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 110;
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 111;

    Context context;
    Intent intent;
    String db_activation = null;

    public LoginActivity() {
        this.context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        View mContentView = findViewById(R.id.fullscreen_content);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        registerListenerforSms();

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.RECEIVE_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST_RECEIVE_SMS);

                // MY_PERMISSIONS_REQUEST_READ_SMS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                        Manifest.permission.READ_SMS)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(LoginActivity.this,
                            new String[]{Manifest.permission.READ_SMS},
                            MY_PERMISSIONS_REQUEST_READ_SMS);

                    // MY_PERMISSIONS_REQUEST_READ_SMS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
           //     registerListenerforSms();
            }
        }


        // intent=new Intent(this,Registration.class);

//        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        //       startActivityForResult(intent,ACTIVITY_REGISTRATION1);

        String username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(KEY_USERNAME, "");
        String password = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(KEY_PASSWORD, "");

        getSupportFragmentManager().beginTransaction().replace(R.id.content, LoginFragment.newInstance(username, password)).commit();


    }

    private void registerListenerforSms() {
        // Permission has already been granted
        SMSReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Toast.makeText(context, "OTP: " + messageText, Toast.LENGTH_LONG).show();
                Log.d("registerListenerforSms", "messageReceived: "+messageText);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS:
            {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

            }
            break;
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  //  registerListenerforSms();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void login(final String userName, final String password) {
        final ProgressDialog waitingDialog = new ProgressDialog(LoginActivity.this);
        waitingDialog.setMessage("درحال ورود\nلظفا چند لحظه منتظر بمانبد...");
        waitingDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Token> call = apiService.getToken("password", password, userName);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                waitingDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("login.onResponse", "onResponse: " + response.body().getToken());
                        ((MyApplication) getApplication()).setCurrentToken(response.body());
                        getProfileDetail(userName, password, response.body().getToken());
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Log.d("login.onResponse", "onResponse: not Success! " +
                                jsonObject.getString("error"));
                        if (jsonObject.getString("error") != null && jsonObject.getString("error").contains("invalid_grant")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("نام کاربری و یا رمز ورود اشتباه است!").create().show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                waitingDialog.dismiss();
                Log.d("login.onFailure", "onFailure " + t.getLocalizedMessage());
                Toast.makeText(LoginActivity.this, "خظایی رویداده است.\nلطفا مجددا تلاش کنید!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getProfileDetail(final String userName, final String password, String token) {
        final ProgressDialog waitingDialog = new ProgressDialog(LoginActivity.this);
        waitingDialog.setMessage("دریافت مشخصات کاربری\nلظفا چند لحظه منتظر بمانبد...");
        waitingDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RetUserProfile> call = apiService.getProfile("Bearer " + token, userName);
        call.enqueue(new Callback<RetUserProfile>() {
            @Override
            public void onResponse(Call<RetUserProfile> call, Response<RetUserProfile> response) {
                waitingDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("login.onResponse", "onResponse: name:" + response.body().getRecord().getName() + " id:" + response.body().getRecord().getId());
                        ((MyApplication) getApplication()).setCurrentUser(response.body().getRecord());
                        Toast.makeText(LoginActivity.this, response.body().getRecord().getName() + " خوش آمدید!", Toast.LENGTH_LONG).show();

                        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        prefs.edit().putString(KEY_USERNAME, userName).apply();
                        prefs.edit().putString(KEY_PASSWORD, password).apply();

                        Intent i = new Intent();
                        i.setClass(LoginActivity.this, MainActivity.class);
                        i.putExtra("KEY", MainActivity.KEY_VARZESHKAR);
                        startActivity(i);
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Log.d("login.onResponse", "onResponse: not Success! " +
                                jsonObject.getString("error"));
                        if (jsonObject.getString("error") != null && jsonObject.getString("error").contains("invalid_grant")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("نام کاربری و یا رمز ورود اشتباه است!").create().show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RetUserProfile> call, Throwable t) {
                waitingDialog.dismiss();
                Log.d("login.onFailure", "onFailure " + t.getLocalizedMessage());
                if (t.getMessage().contains("No address associated with hostname"))
                    Toast.makeText(LoginActivity.this, "خظایی رویداده است. آبا به اینترنت متصل هستید؟\nلطفا مجددا تلاش کنید!", Toast.LENGTH_LONG).show();

                Toast.makeText(LoginActivity.this, "خظایی رویداده است.\nلطفا مجددا تلاش کنید!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void gotoRegistrationPage(int choice) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, RegisterFragment.newInstance(choice))
                .addToBackStack("1")
                .commit();

    }

    @Override
    public void register() {

    }

    @Override
    public void gotoLoginPage() {

        getSupportFragmentManager().beginTransaction().replace(R.id.content, new LoginFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
