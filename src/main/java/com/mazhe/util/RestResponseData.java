package com.mazhe.util;

public interface RestResponseData<T> {
    static <T> RestResponseData<T> create(T data) {

        return new RestResponseDataWrapper<>(data);
    }

    T getData();
}
