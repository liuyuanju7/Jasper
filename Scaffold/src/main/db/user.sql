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