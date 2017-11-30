package com.lxf.bigdata.dao.redis.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextUtil {
    public static ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("spring-context-jedis-cluster.xml");

}
