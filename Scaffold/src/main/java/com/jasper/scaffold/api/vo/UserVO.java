package com.jasper.scaffold.api.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户表实体
 *
 * @author codegenerator
 * @date 2020-06-28
 */

@Data
@Accessors(chain = true)
public class UserVO implements Serializable {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

}
