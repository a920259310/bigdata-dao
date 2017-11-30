package com.lxf.bigdata.dao.redis.test;

import com.lxf.bigdata.dao.redis.utils.RedisUtil;

public class TestMain {
    public static void main(String[] args) {
        RedisUtil redisUtil = new RedisUtil();

        redisUtil.setCacheObject("lxf","456456456");

        Object lxf = redisUtil.getCacheObject("lxf");

        System.out.println(lxf);
    }
}
