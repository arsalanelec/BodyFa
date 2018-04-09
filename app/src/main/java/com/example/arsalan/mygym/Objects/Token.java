package com.example.arsalan.mygym.Objects;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    String token;
    @SerializedName("expires_in")
    int exprieIn;
    public Token() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExprieIn() {
        return exprieIn;
    }

    public void setExprieIn(int exprieIn) {
        this.exprieIn = exprieIn;
    }
}
