package com.lxf.bigdata.dao.kafka.test;

import com.lxf.bigdata.dao.kafka.utils.SpringContextUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        KafkaTemplate kafkaTemplate = SpringContextUtil.appContext.getBean(KafkaTemplate.class);

        ListenableFuture send = kafkaTemplate.send("test1", "123a");
//
        kafkaTemplate.flush();
        System.out.println(send);

        Thread.sleep(1000000000);
    }
}
