package com.example.t.myapplication.model.parser;

/**
 * Created by T on 2016/9/12.
 */
public class NewsCommentNum {
    private int data;

    public NewsCommentNum(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NewsCommentNum{" +
                "data=" + data +
                '}';
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
