package com.example.arsalan.mygym;

import android.app.Application;

import com.example.arsalan.mygym.Objects.Token;
import com.example.arsalan.mygym.Objects.User;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Arsalan on 10-02-2018.
 */

public class MyApplication extends Application{
    private Token currentToken;
    private User currentUser;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        currentToken=new Token();
        currentUser=new User();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }
}
