package com.jasper.scaffold.common.mvc;

/**
 * @author liuyuanju1
 * @date 2020/7/2
 * @description: Result统一生成类
 */
public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    private ResultGenerator() {
    }


    public static Result genSuccessResult() {
        return new Result().setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result().setMessage(DEFAULT_SUCCESS_MESSAGE).setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL.getCode())
                .setMessage(message);
    }

    public static Result genRedirctResult(Object data) {
        return new Result()
                .setCode(ResultCode.REDIRECT.getCode())
                .setData(data);
    }
}
