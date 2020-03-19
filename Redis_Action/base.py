#!/usr/bin/env python3
# -*- coding=utf-8 -*-

"""
base data struct
"""

import redis

# 创建一个redis实例，获取一个新的连接 -- 本地redis需要已启动: redis-server
r = redis.Redis(host="localhost", port=6379)

# redis - 字符串
r.set("k1", "v1")
r.set("k2", "v2")
print(r.get("k1"))
print(r.get("k2"))

# redis - 列表 允许重复元素
r.rpush("list-key", "item1")  # 将给定的值推入列表的右端
r.rpush("list-key", "item2")
r.rpush("list-key", "item2")
print(r.lrange("list-key", 0, -1))  # 通过范围取值
print(r.lindex("list-key", 1))  # 通过index取值
print(r.lpop("list-key"))  # 从列表中弹出一个元素
print(r.lrange("list-key", 0, -1))
r.delete("list-key")


# redis - 集合 不允许重复元素
r.sadd("set-key", "item1")
r.sadd("set-key", "item2")
r.sadd("set-key", "item3")
print(r.smembers("set-key"))  # 获取key对应集合的全部元素
print(r.sismember("set-key", "item1"))  # 检查是否存在
r.srem("set-key", "item1")
print(r.sismember("set-key", "item1"))
r.delete("set-key")

# redis - 散列
r.hset("hash-key", "sub-key1", "value1")
r.hset("hash-key", "sub-key2", "value2")
print(r.hget("hash-key", "sub-key1"))
print(r.hgetall("hash-key"))
r.hdel("hash-key", "sub-key1")
print(r.hgetall("hash-key"))
r.delete("hash-key")

# redis - 有序集合 python3 的新写法
r.zadd("zset-key", {"member1": 666, "member2": 777})
print(r.zrange("zset-key", 0, -1, withscores=True))
print(r.zrangebyscore("zset-key", 600, 800, withscores=True))
print(r.zrem("zset-key", "member1"))
r.delete("zset-key")

