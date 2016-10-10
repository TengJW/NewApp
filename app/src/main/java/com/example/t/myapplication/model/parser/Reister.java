package com.example.t.myapplication.model.parser;

/**
 * Created by T on 2016/9/8.
 */
public class Reister {

    private int result;
    private String explain;
    private String token;

    public Reister(int result, String explain, String token) {
        this.result = result;
        this.explain = explain;
        this.token = token;
    }

    @Override
    public String toString() {
        return "Reister{" +
                "result=" + result +
                ", explain='" + explain + '\'' +
                ", token='" + token + '\'' +
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
