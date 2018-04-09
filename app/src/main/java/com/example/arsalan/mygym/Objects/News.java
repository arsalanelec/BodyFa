package com.example.arsalan.mygym.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Arsalan on 10-02-2018.
 */

public class News {
    String title;
    @SerializedName("Description")
    String desc;
    @SerializedName("NewsDateFa")
    String date;

    @SerializedName("ViewCount")
    int visitcnt;

    @SerializedName("LikeCount")
    int likeCnt;

    @SerializedName("CommentCount")
    int commentCnt;
    String thumbUrl;

    public String getThumbUrl() {
        return thumbUrl;
    }
    public News() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVisitcnt() {
        return visitcnt;
    }

    public void setVisitcnt(int visitcnt) {
        this.visitcnt = visitcnt;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public int getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
    }
}
