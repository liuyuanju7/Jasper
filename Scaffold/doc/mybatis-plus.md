## Mybatis-plus 常用功能集成

> - 本项目使用 使用 [Spring Initializer](https://start.spring.io/) 初始化的 Spring Boot 工程； 版本：2.3.1
>
> - MyBatis-Plus官方地址：[MyBatis-Plus](https://mp.baomidou.com/)  版本：3.1.0
> - 示例项目Github地址：[scaffold-project](https://github.com/liuyuanju7/Jasper/tree/master/scaffold)

[TOC]

#### 1、基本使用

- 添加maven依赖

  - mybatis-plus 基础依赖

    ```xml
    <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.1.0</version>
    </dependency>
    ```

  - 添加 代码生成器 依赖

    ```xml
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-generator</artifactId>
        <version>3.1.0</version>
    </dependency>
    ```

  - 添加 模板引擎 依赖 (如果需要自定义代码生成模板), MyBatis-Plus 支持 Velocity（默认）使用其他自定义模板引擎需要额外配置

    ```xml
    <dependency>
        <groupId>org.apache.velocity</groupId>
        <artifactId>velocity-engine-core</artifactId>
        <version>2.2</version>
    </dependency>
    ```

- 在`application.yml`配置文件中添加基础配置

  ```yml
  # 基础 DataSource Config
  spring:
    datasource:
      druid:
        url: jdbc:mysql://localhost:3306/scaffold?useUnicode=true&characterEncoding=utf8&useSSL=false
        username: mysql
        password: mysql
      driver-class-name: com.mysql.jdbc.Driver
      type: com.alibaba.druid.pool.DruidDataSource
  
  # 更多配置
  # ====================MybatisPlus Config====================
  mybatis-plus:
    # 如果是放在src/main/java目录下 classpath:/com/yourpackage/*/mapper/*Mapper.xml
    # 如果是放在resource目录 classpath*:/mapper/*Mapper.xml
    mapper-locations: classpath:/com/jasper/scaffold/**/mapper/xml/*Mapper.xml
    #实体扫描，多个package用逗号或者分号分隔
    typeAliasesPackage: com.jasper.scaffolds.*.entity
    global-config:
      key-generator: com.baomidou.mybatisplus.incrementer.OracleKeyGenerator
      #主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
      id-type: 2
      #字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
      field-strategy: 2
      #驼峰下划线转换
      db-column-underline: true
      db-config:
        #mp2.3+ 全局表前缀 mp_
        #table-prefix: mp_
        #刷新mapper 调试神器
        #refresh-mapper: true
        #数据库大写下划线转换
        #capital-mode: true
        # 逻辑删除配置
        logic-delete-field: mark # 全局逻辑删除字段 实体类上有 @TableLogic 则以实体上的为准，忽略全局
        logic-delete-value: 1
        logic-not-delete-value: 0
  
      #自定义填充策略接口实现
      #meta-object-handler: com.baomidou.springboot.MyMetaObjectHandler
    configuration:
      #配置返回数据库(column下划线命名&&返回java实体是驼峰命名)，自动匹配无需as（没开启这个，SQL需要写as： select user_id as userId）
      map-underscore-to-camel-case: true
      cache-enabled: false
      #配置JdbcTypeForNull, oracle数据库必须配置
      jdbc-type-for-null: 'null'
  #===================================================================================
  ```

- 启动类中添加 `@MapperScan` 注解，扫描 Mapper 文件夹

  ```java
  @SpringBootApplication
  @MapperScan("com.jasper.scaffold.*.mapper")
  public class ScaffoldApplication {
  
  	public static void main(String[] args) {
  		SpringApplication.run(ScaffoldApplication.class, args);
  	}
  
  }
  
  ```

- 测试数据库 `scaffold` Table: `User`

  ```sql
  -- 数据库： scaffold
  DROP TABLE IF EXISTS `user`;
  CREATE TABLE `user` (
    `id` bigint(20) NOT NULL COMMENT '主键ID',
    `name` varchar(30) DEFAULT NULL COMMENT '姓名',
    `age` int(11) DEFAULT NULL COMMENT '年龄',
    `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
    `cTime` datetime DEFAULT NULL COMMENT '创建时间',
    `uTime` datetime DEFAULT NULL COMMENT '修改时间',
    `mark` tinyint(1) DEFAULT NULL COMMENT '数据有效标示位 1无效 0有效',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表实体';
  ```

#### 2、代码生成器

- 通过`MyBatis-Plus` 的代码生成器`AutoGenerator`，通过数据库表 直接快速生成tity、Mapper、Mapper XML、Service、Controller 等各个模块的代码

- 单独创建`MpGenerator`生成类，执行 main 方法生成指定 table 对应的模块代码

- 更多属性及配置请查看：[更多配置](https://mp.baomidou.com/guide/generator.html#编写配置)

  ```java
  // 自定义代码生成类 demo
  public class MpGenerator {
      public static void main(String[] args) {
          //fixme 生成的java文件路径
          String javaPath = "/xxx/git-hub/Jasper/scaffold/src/main/java";
          //fixme 包名
          String packgeName = "com.jasper.scaffold.api";
  
          //fixme mysql数据库配置
          String dbusername = "mysql";
          String dbpassword = "mysql";
          String dbip = "localhost";
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
  ```

#### 3、CRUD接口

- `Mapper层` CRUD接口简单示例： [更多接口列表详情]([https://mp.baomidou.com/guide/crud-interface.html#mapper-crud-%E6%8E%A5%E5%8F%A3](https://mp.baomidou.com/guide/crud-interface.html#mapper-crud-接口))

  >- 通用 CRUD 封装[BaseMapper](https://gitee.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-core/src/main/java/com/baomidou/mybatisplus/core/mapper/BaseMapper.java)接口，为 `Mybatis-Plus` 启动时自动解析实体表关系映射转换为 `Mybatis` 内部对象注入容器
  >- 泛型 `T` 为任意实体对象
  >- 参数 `Serializable` 为任意类型主键 `Mybatis-Plus` 不推荐使用复合主键约定每一张表都有自己的唯一 `id` 主键
  >- 对象 `Wrapper` 为 [条件构造器](https://mp.baomidou.com/guide/wrapper.html)
  >- 方法名均为这四种关键字：`Insert` `Delete` `Update` `Select`

  ```java
  // 插入一条记录
  int insert(T entity);
  // 根据 entity 条件，删除记录
  int delete(@Param(Constants.WRAPPER) Wrapper<T> wrapper);
  // 根据 ID 修改
  int updateById(@Param(Constants.ENTITY) T entity);
  // 根据 ID 查询
  T selectById(Serializable id);
  // 根据 entity 条件，查询一条记录
  T selectOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
  ```

- `Service层` CRUD接口简单示例： 

  > 说明:
  >
  > - 通用 Service CRUD 封装[IService](https://gitee.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-extension/src/main/java/com/baomidou/mybatisplus/extension/service/IService.java)接口，进一步封装 CRUD 采用 `get 查询单行` `remove 删除` `list 查询集合` `page 分页` 前缀命名方式区分 `Mapper` 层避免混淆，
  > - 泛型 `T` 为任意实体对象
  > - 建议如果存在自定义通用 Service 方法的可能，请创建自己的 `IBaseService` 继承 `Mybatis-Plus` 提供的基类
  > - 对象 `Wrapper` 为 [条件构造器](https://mp.baomidou.com/guide/wrapper.html)

  - 基础接口列表详情参见：

    [基础接口列表详情]([https://mp.baomidou.com/guide/crud-interface.html#service-crud-%E6%8E%A5%E5%8F%A3](https://mp.baomidou.com/guide/crud-interface.html#service-crud-接口))

  - `链式查询` 测试

    ```java
    // 链式查询 普通
    QueryChainWrapper<T> query();
    // 链式查询 lambda 式。注意：不支持 Kotlin
    LambdaQueryChainWrapper<T> lambdaQuery(); 
    ```

    ```java
    @Test
    void testChainQuery() {
        // 链式查询 普通
        User user = userService.query().eq("name", "jasper").one();
        // 链式查询 lambda 式
        User user2 = userService.lambdaQuery().eq(User::getName, "jasper").one();
        System.out.println(user);
        System.out.println(user2);
    }
    ```

  - `链式更新` 测试

    ```java
    // 链式更改 普通
    UpdateChainWrapper<T> update();
    // 链式更改 lambda 式。注意：不支持 Kotlin 
    LambdaUpdateChainWrapper<T> lambdaUpdate();
    ```

    ```java
    @Test
    void testChainUpdate() {
        // 链式更改 普通
        String queryVal = "zhangsan";
        userService.update().eq("name", queryVal).remove();
        // 链式更改 lambda 式
        User updateEntity = new User().setEmail("1111@qq.com");
        userService.lambdaUpdate().eq(User::getName, "lisi").update(updateEntity);
    }
    ```

#### 4、分页插件

- 在配置类中 开启分页插件, 支持额外的分页配置，如：最大单页数量限制

  ```java
  @Configuration
  @EnableTransactionManagement
  public class MybatisPlusConfig {
  
      /**
       * 分页插件
       * @return
       */
      @Bean
      public PaginationInterceptor paginationInterceptor() {
          return new PaginationInterceptor();
      }
  }
  ```

- 测试分页查询

  ```java
  @Test
  void testPagination() {
      Page<User> page = new Page<>(1, 5);
      page.setDesc("age");
      IPage<User> userPage = userMapper.selectPage(page, Wrappers.<User>query().gt("age", 5));
      log.error("总条数 -------------> {}", userPage.getTotal());
      log.error("当前页数 -------------> {}", userPage.getCurrent());
      log.error("当前每页显示数 -------------> {}", userPage.getSize());
      List<User> records = userPage.getRecords();
      log.error("records: {}", records);
  }
  ```

- 测试`lambda`分页查询

  ```java
  @Test
  void lambdaPagination() {
      Page<User> page = new Page<>(1, 3);
      IPage<User> result = userMapper.selectPage(page, Wrappers.<User>lambdaQuery().ge(User::getAge, 1).orderByAsc(User::getAge));
      log.error("总条数 -------------> {}", result.getTotal());
      log.error("当前页数 -------------> {}", result.getCurrent());
      log.error("当前每页显示数 -------------> {}", result.getSize());
      List<User> records = result.getRecords();
      log.error("records: {}", records);
  }
  ```

#### 5、逻辑删除

​	`mybatis-plus` 支持数据的逻辑删除，当数据库记录设计是通过 某个字段来标示 数据是否有效或者是否已删除，而不是真正的物理删除时， 可以通过配置，将`mybatis-plus`默认的`删除`变成逻辑删除；并且`查询`方法也会默认带上逻辑删除标示，即只查询 未删除的数据 （自己扩展的xml不会），当有需求 就是需要查询逻辑删除的数据时，可手写查询 （既然设计了逻辑删除，一般不会查询已删除的数据）

- `application.yml `加入配置

  ```yml
  mybatis-plus:
    global-config:
      db-config:
        logic-delete-field: flag  #全局逻辑删除字段值3.3.0开始支持 flag可以修改为自己的 逻辑标示字段
        logic-delete-value: 1 # 逻辑已删除值(默认为 1)
        logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
  ```

- 实体类字段上加上`@TableLogic`注解

  ```java
  /**
  * 数据有效标示位 0有效 1无效
  */
  @TableLogic
  private Boolean mark;
  ```

  > Tip:
  >
  > - 字段支持所有数据类型(推荐使用 `Integer`,`Boolean`,`LocalDateTime`)
  > - 如果使用`LocalDateTime`,建议逻辑未删除值设置为字符串`null`,逻辑删除值只支持数据库函数例如`now()`
  > - 使用mp自带方法删除和查找都会附带逻辑删除功能 (自己写的xml不会)
  > - 如果实体类上有 @TableLogic 则以实体上的为准，忽略全局。 即先查找注解再查找全局，都没有则此表没有逻辑删除

#### 6、通用枚举

在一些情况下，我们希望实体的字段是固定的几个值，比如：`User用户表`的`性别(Gender)`，我们希望是固定的`Male`,`Female` 我们可能在设计数据库字段时，采用了`int(1)`:

- 1: 男
- 2: 女

如果实体使用了`Integer`，那么无法保证新增时传入的参数是1/2;此种场景就可以用通用枚举来处理

- 在`application.yml`中添加 配置枚举类扫描路径

  ```yml
  mybatis-plus:
    # 支持统配符 * 或者 ; 分割
    type-enums-package: com.jasper.scaffold.api.entity.enums
    ...
  ```

- 先定义一个性别的枚举类

  ```java
  @Getter
  public enum GenderEnum {
  
      MALE(1, "男"),
      FEMALE(2, "女");
  
      private final int code;  // 数据库中 存储的为 code
      private final String descp;
  
      GenderEnum(int code, String descp){
          this.code = code;
          this.descp = descp;
      }
  }
  ```

- 定义用户实体时，使用枚举类型定义 性别

  ```java
     /**
       * 性别
       */
      private GenderEnum gender;
  // 省略其他。。
  ```

- 添加枚举配置

  - 方式1： 使用 `@EnumValue` 注解枚举属性

    ```java
    @EnumValue  //标记数据库存的值是code
    private final int code;  
    ```

  - 方式2：枚举属性，实现 `IEnum 接口`

    ```java
    public enum GenderEnum implements IEnum<Integer>{
    
        MALE(1, "男"),
        FEMALE(2, "女");
    
        private final int code;  // 数据库中 存储的为 code
        private final String descp;
    	
        @Override
        public Integer getValue() {
            return this.code;
        }
        
        GenderEnum(int code, String descp){
            this.code = code;
            this.descp = descp;
        }
    }
    ```

- 测试枚举使用

  ```java
  @Test
  void testEnum() {
      User user = new User()
          .setGender(GenderEnum.MALE)
          .setAge(12)
          .setEmail("1234@11.com")
          .setName("Name_y")
          .setMark(false); // false 未删除
      // 插入
      userMapper.insert(user);
  	// 查询
      List<User> userList = userService.lambdaQuery().eq(User::getGender, GenderEnum.MALE).list();
      log.error("用户数：{}", userList.size());
  }
  // 用户数：2
  ```

#### 7、自动填充功能

​		当我们在插入、修改数据库记录时，想要填充某个字段的值时：比如 `User`表的 `cTime`、`uTime`字段，当新增数据时，自动设置 `cTime`、`uTime`为当前时间，当更新修改数据时，自动修改`uTime`为当前时间，这个时候就可以使用自动填充功能

- 实现元对象处理器接口：`com.baomidou.mybatisplus.core.handlers.MetaObjectHandler`

  ```java
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
  ```

- 注解填充字段 `@TableField(.. fill = FieldFill.INSERT)`

  ```java
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
  ```

  > Tip:  
  >
  > - 字段必须声明`TableField`注解,属性`fill`选择对应策略,该声明告知`Mybatis-Plus`需要预留注入`SQL`字段
  > - 填充处理器`MyMetaObjectHandler`在 Spring Boot 中需要声明`@Component`或`@Bean`注入
  > - 要想根据注解`FieldFill.xxx`和`字段名`以及`字段类型`来区分必须使用父类的`strictInsertFill`或者`strictUpdateFill`方法
  > - 不需要根据任何来区分可以使用父类的`fillStrategy`方法
  >
  > - 字段类型是 `LocalDateTime`时， 集成druid数据源，使用3.1.0之前版本没问题，升级mp到3.1.1+后，运行时报错:`java.sql.SQLFeatureNotSupportedException`

#### 8、SQL性能分析

​		可以通过自带的SQL性能分析插件，自动打印出执行的SQL以及执行时间，方便排查问题以及优化性能较慢的SQL，可以设置`Profile`只在 `dev、test`环境下启动

- 在`MybatisPlusConfig`启动SQL性能分析插件

  ```java
  /**
  * SQL 性能分析插件
  * @return
  */
  @Bean
  @Profile({"dev", "test"})
  public PerformanceInterceptor performanceInterceptor() {
      return new PerformanceInterceptor();
  }
  ```

- 自动分析执行SQL

  ```mysql
  Time：39 ms - ID：com.jasper.scaffold.api.mapper.UserMapper.selectList
  Execute SQL：SELECT id,name,gender,age,email,cTime,uTime,mark FROM user WHERE gender = 1
  ```

#### 9、多数据源

​		`dynamic-datasource-spring-boot-starter` 是一个基于springboot的快速集成多数据源的启动器，可实现快速切换多个数据源: [详情](https://mp.baomidou.com/guide/dynamic-datasource.html)

- 引入Maven包

  ```xml
  <dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
    <version>${version}</version>
  </dependency>
  ```

- 配置多个数据源

  ```yml
  spring:
    datasource:
      dynamic:
        primary: master #设置默认的数据源或者数据源组,默认值即为master
        strict: false #设置严格模式,默认false不启动. 启动后在未匹配到指定数据源时候回抛出异常,不启动会使用默认数据源.
        datasource:
          master:
            url: jdbc:mysql://xx.xx.xx.xx:3306/dynamic
            username: root
            password: 123456
            driver-class-name: com.mysql.jdbc.Driver
          slave_1:
            url: jdbc:mysql://xx.xx.xx.xx:3307/dynamic
            username: root
            password: 123456
            driver-class-name: com.mysql.jdbc.Driver
          slave_2:
            url: ENC(xxxxx) # 内置加密,使用请查看详细文档
            username: ENC(xxxxx)
            password: ENC(xxxxx)
            driver-class-name: com.mysql.jdbc.Driver
            schema: db/schema.sql # 配置则生效,自动初始化表结构
            data: db/data.sql # 配置则生效,自动初始化数据
            continue-on-error: true # 默认true,初始化失败是否继续
            separator: ";" # sql默认分号分隔符
            
         #......省略
         #以上会配置一个默认库master，一个组slave下有两个子库slave_1,slave_2
  ```

- 使用 **@DS** 切换数据源

  **@DS** 可以注解在方法上和类上，**同时存在方法注解优先于类上注解**。建议只注解在service实现上

- 示例

  ```java
  @Service
  @DS("slave")
  public class UserServiceImpl implements UserService {
  
    @Autowired
    private JdbcTemplate jdbcTemplate;
  
    public List<Map<String, Object>> selectAll() {
      return  jdbcTemplate.queryForList("select * from user");
    }
    
    @Override
    @DS("slave_1")
    public List<Map<String, Object>> selectByCondition() {
      return  jdbcTemplate.queryForList("select * from user where age >10");
    }
  }
  ```

  

