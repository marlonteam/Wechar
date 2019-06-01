package com.mazhe.util;

public class RestResponseDataWrapper<T> implements RestResponseData<T> {
    public RestResponseDataWrapper() {

    }

    public RestResponseDataWrapper(T data) {

        this.data = data;
    }

    private T data;

    @Override
    public T getData() {

        return data;
    }

    public void setData(T data) {

        this.data = data;
    }
}
