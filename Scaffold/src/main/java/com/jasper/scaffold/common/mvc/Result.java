package com.jasper.scaffold.common.mvc;

import com.alibaba.fastjson.JSON;

/**
 * @author liuyuanju1
 * @date 2020/7/2
 * @description: 统一API响应结果封装
 */
public class Result<T> {

    private int code = 200;

    private String message;

    private T data;

    Result() {
    }

    public Result(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
