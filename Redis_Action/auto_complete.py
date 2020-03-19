#!/usr/bin/env python3
# -*- coding=utf-8 -*-

"""
@Name:   auto_complete
@Author: liuyuanju
@Date:   2020-03-19
@Description: 自动补全最近联系人,最多只保留100位
"""
import redis


# 添加或者更新一个联系人，使其成为最新的被联系用户
def add_update_contract(conn, user, contract):
    ac_list = 'recent:' + user
    pipeline = conn.pipeline(True)
    pipeline.lrem(ac_list, 1, contract)
    pipeline.lpush(ac_list, contract)
    pipeline.ltrim(ac_list, 0, 99)
    pipeline.execute()


# 将指定联系人移除联系人列表
def remove_contract(conn, user, contract):
    conn.lrem('recent:' + user, 1, contract)


# 获取自动补全列表并查找匹配的用户
def fetch_autocpmplete_list(conn, user, prefix):
    canditates = conn.lrange('recent:' + user, 0, -1)
    matches = []
    for canditate in canditates:
        s = str(canditate)
        if s.lower().startswith(prefix):
            matches.append(canditate)
    return matches


if __name__ == '__main__':
    conn = redis.Redis(host="localhost", port=6379, decode_responses=True)
    add_update_contract(conn, 'liuyuanju', 'zhangsan')
    add_update_contract(conn, 'liuyuanju', 'lisi')
    add_update_contract(conn, 'liuyuanju', 'wangwu')
    print(fetch_autocpmplete_list(conn, 'liuyuanju', 'zhang'))

