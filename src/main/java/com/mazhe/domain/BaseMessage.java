package com.mazhe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessage<T> {


	private String code;  // 0:failed, 1:success
	private String message;  // 0:failed, 1:success
    private T data;
    public  static  BaseMessage  Success(Object t){
        return new BaseMessage("200","请求成功",t);
    }
    public  static  BaseMessage  Null(Object t){
        return new BaseMessage("501","请求数据为空",null);
    }

    public  static  BaseMessage Fail(String message){
        return new BaseMessage("500",message,null);
    }
}
