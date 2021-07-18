package com.jasper.scaffold.common.mvc;

/**
 * @author liuyuanju1
 * @date 2020/7/2
 * @description: 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {

    SUCCESS(200),//成功
    FAIL(400),//失败
    REDIRECT(304),//跳转URL
    UNAUTHORIZED(401),//未认证（签名错误）
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500),//服务器内部错误
    EXIST(409); //已经存在

    private int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
