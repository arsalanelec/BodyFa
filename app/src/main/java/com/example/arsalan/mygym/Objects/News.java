package com.example.arsalan.mygym.Objects;

/**
 * Created by Arsalan on 10-02-2018.
 */

public class News {
    String title;
    String desc;
    String date;
    int visitcnt;
    int likeCnt;
    int commentCnt;

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
