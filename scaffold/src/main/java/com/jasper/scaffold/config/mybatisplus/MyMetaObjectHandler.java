package com.jasper.scaffold.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author liuyuanju1
 * @date 2020/7/1
 * @description: 数据库字段自动填充处理器
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("start insert fill ....");
        // 起始版本 3.3.0(推荐使用)
        // this.strictInsertFill(metaObject, "cTime", Date.class, new Date());
        this.setFieldValByName("cTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("start update fill ....");
        // 起始版本 3.3.0(推荐使用)
        // this.strictUpdateFill(metaObject, "uTime", Date.class, new Date());
        this.setFieldValByName("uTime", new Date(), metaObject);
    }
}
