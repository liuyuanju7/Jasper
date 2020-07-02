package com.jasper.scaffold.api.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author liuyuanju1
 * @date 2020/6/28
 * @description: 性别枚举
 */
@Getter
public enum GenderEnum {

    MALE(1, "男"),
    FEMALE(2, "女");

    @EnumValue
    private final int code;  // 数据库中 存储的为 code

    private final String descp;

    GenderEnum(int code, String descp){
        this.code = code;
        this.descp = descp;
    }
}
