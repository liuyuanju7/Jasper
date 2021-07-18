package com.jasper.scaffold.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuanju1
 * @date 2020/6/28
 * @description: mybatis-plus 代码自动生成工具 更多配置可参考 官方配置手册
 */
@Slf4j
public class MpGenerator {
    public static void main(String[] args) {
        //fixme 生成的java文件路径
        String javaPath = "/Users/liuyuanju1/study/git-hub/Jasper/scaffold/src/main/java";
        //fixme 包名
        String packgeName = "com.jasper.scaffold.api";

        //fixme mysql数据库配置
        String dbusername = "mysql";
        String dbpassword = "mysql";
        String dbip = "192.168.168.65";
        String dbname = "scaffold";

        // 各种配置
        AutoGenerator mpg = new AutoGenerator();
        // 设置数据源
        mpg.setDataSource(new DataSourceConfig()
                .setDriverName("com.mysql.jdbc.Driver")
                // 设置数据库类型
                .setDbType(DbType.MYSQL)
                .setUsername(dbusername)
                .setPassword(dbpassword)
                .setUrl(String.format("jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull", dbip, dbname))
        );

        // 全局配置
        mpg.setGlobalConfig(new GlobalConfig()
                // 输出目录
                .setOutputDir(javaPath)
                // 是否覆盖
                .setFileOverride(true)
                // 开启AR模式
                .setActiveRecord(true)
                // XML二级缓存
                .setEnableCache(false)
                // 生成ResultMap
                .setBaseResultMap(true)
                // 生成 sql片段
                .setBaseColumnList(true)
                // 自动打开生成后的文件夹
                .setOpen(true)
                // 所有文件的生成者
                .setAuthor("codegenerator")
                .setDateType(DateType.ONLY_DATE)
                // 自定义文件命名,%s会自动填充表实体类名字
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("I%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController")
        );


        // 策略配置
        mpg.setStrategy(new StrategyConfig()
                // fixme 添加需要生成的表
                .setInclude("user")
                // 实体类使用Lombok
                .setEntityLombokModel(true)
                // 表名生成策略,下划线转驼峰
                .setNaming(NamingStrategy.underline_to_camel)
                .setRestControllerStyle(true)
                .setSuperControllerClass("com.jasper.scaffold.common.mvc.BaseController")
                .setLogicDeleteFieldName("mark")
        );

        // 包配置
        mpg.setPackageInfo(new PackageConfig()
                // 基本包路径
                .setParent(packgeName)
                // 设置Controller包名
                .setController("web")
                // 设置entity包名
                .setEntity("entity")
                // 设置Mapper包名
                .setMapper("mapper")
                // 设置Service包名
                .setService("service")
                // 设置Service实现类包名
                .setServiceImpl("service.impl")
                // 设置Mapper.xml包名
                .setXml("mapper.xml")
        );

        // 如果需要定制 代码生成模板 可以 自己写代码模板 如： 自定义 VO模板
        List<FileOutConfig> list = new ArrayList<>();
        list.add(new FileOutConfig("/templates/codegenerator/vo.java.vm") {
            // 自定义vo输出路径
            @Override
            public String outputFile(TableInfo tableInfo) {
                String voPath = javaPath.concat("/").concat(packgeName.replaceAll("[.]", "/"))
                        .concat("/").concat("vo").concat("/")
                        .concat(tableInfo.getEntityName()).concat("VO.java");
                log.info("voPath:{}", voPath);
                return voPath;

            }
        });

//        list.add(new FileOutConfig("/templates/mapper.xml.vm") {
//            // 自定义Mapper.xml输出路径
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return "/Users/liqingchao/work/xingyun_pmp/project_be/project_be/proj-server/src/main/java/com/jd/jacp/proj/project/mapper/xml/" + tableInfo.getEntityName() + "Mapper.xml";
//            }
//        });

        // 注入自定义配置
        mpg.setCfg(new InjectionConfig() {
            @Override
            public void initMap() {
                // 注入自定义 Map 对象(注意需要setMap放进去)
                Map<String, Object> map = new HashMap<>(1);
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.getConfig().getPackageInfo().put("Vo", mpg.getPackageInfo().getParent() + ".vo");
                this.setMap(map);
            }
        }.setFileOutConfigList(list));

        mpg.execute();
    }
}
