package com.lxf.bigdata.dao.kafka.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextUtil {
    public static ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("spring-producer.xml","spring-consumer.xml");
}
