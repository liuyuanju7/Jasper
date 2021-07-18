package com.jasper.scaffold.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jasper.scaffold.api.entity.enums.GenderEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表实体
 * </p>
 *
 * @author codegenerator
 * @since 2020-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    @TableField(value = "cTime", fill = FieldFill.INSERT)
    private Date cTime;

    /**
     * 修改时间
     */
    @TableField(value = "uTime", fill = FieldFill.UPDATE)
    private Date uTime;

    /**
     * 数据有效标示位 0有效 1无效
     */
    @TableLogic
    private Boolean mark;

//    @Version
//    private Integer version;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
