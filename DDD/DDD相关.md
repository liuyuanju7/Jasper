## DDD-领域驱动模型

>@referance http://www.likecs.com/default/index/show?id=93970
>
>@referance https://blog.csdn.net/wwd0501/article/details/95062535



### DDD的概念

- 实体
- 值对象
- 聚合
- 聚合根
- 领域服务
- 领域事件

### 如何DDD

#### 1、界限上下文

- 问题空间 - `problem space`
- 解决方案空间 `solution space`
- 子域和界限上下文一一对应

### 2、Repository

- Repository是一个独立的层，介于领域层与数据访问层之间
- Dao 与 Repository 区别
  - 设计Repository时，我们应该采用面向集合的方式，而不是面向数据访问的方式（crud）
  - Repository是面向领域的，Repository定义的目的不是DB驱动的
  - Repository管理的数据的最小粒度是聚合根

建议把Repository定义为一个集合并且只提供类似集合的接口，比如Add，Remove，Get这种操作。一言以蔽之，我们要用集合的思想来操作聚合根，而不是传统的面向DB的CRUD方法。