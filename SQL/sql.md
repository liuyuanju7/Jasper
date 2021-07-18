##### 查询一张表的数据，insert到另一张表

```mysql
-- filed1, filed2 为老表中的字段， 后边的是固定初始值的字段
insert into tableNew
(
req_id,
sys_id,
create_erp,
create_time,
update_erp,
update_time,
mark
) select filed1,field2,"admin",NOW(),null,null,0 from tableNew where id = 2147
```

##### SQL查询一个表中类别字段中Max()最大值对应的记录

```sql
-- 构造两张表关联查
SELECT A.id,  
       A.name,  
       A.version  
FROM   DOC A,  
       (SELECT id,  
               MAX(version) AS version  
        FROM   DOC  
        GROUP  BY id) AS B  
WHERE  A.id = B.id  
       AND A.version = B.version 
       
-- 利用order by 将想要的数据排在第一位

select b.name, max(b.dealtime) from
( 
  select * 
  from table_A 
 	group by name
 	order by dealtime desc
) b
```

##### 查询指定数据库的所有表

```sql
select table_name from information_schema.TABLES where table_schema='db_name'; 
```

