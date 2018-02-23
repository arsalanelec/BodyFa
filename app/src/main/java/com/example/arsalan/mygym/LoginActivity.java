package com.example.arsalan.mygym;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.arsalan.mygym.fragments.LoginFragment;
import com.example.arsalan.mygym.fragments.RegisterFragment;

public class LoginActivity extends AppCompatActivity implements
        LoginFragment.OnFragmentInteractionListener
        , RegisterFragment.OnFragmentInteractionListener {


    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mContentView = findViewById(R.id.fullscreen_content);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new LoginFragment()).commit();


    }

    @Override
    public void login() {

    }

    @Override
    public void gotoRegisterationPage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new RegisterFragment()).commit();

    }

    @Override
    public void register() {

    }

    @Override
    public void gotoLoginPage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new LoginFragment()).commit();

    }
}
