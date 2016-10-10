package com.example.t.myapplication.model.parser;

/**
 * Created by T on 2016/9/13.
 */
public class NewsSendComment {
    private int result;
    private String explain;

    @Override
    public String toString() {
        return "NewsSendComment{" +
                "result=" + result +
                ", explain='" + explain + '\'' +
                '}';
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public NewsSendComment(int result, String explain) {

        this.result = result;
        this.explain = explain;
    }
}
