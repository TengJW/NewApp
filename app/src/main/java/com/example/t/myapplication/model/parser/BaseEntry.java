package com.example.t.myapplication.model.parser;

/**
 * Created by T on 2016/9/6.
 */
public class BaseEntry<T> {
    private  String message;
    private  int status;
    private T data;

    @Override
    public String toString() {
        return "BaseEntry{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseEntry(String message, int status, T data) {

        this.message = message;
        this.status = status;
        this.data = data;
    }
}
