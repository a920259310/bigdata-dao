package com.lxf.bigdata.dao.kafka.consumer.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

public class KafkaConsumerListener implements MessageListener<Integer, String> {
    protected final Logger LOG = LoggerFactory.getLogger(KafkaConsumerListener.class);
    @Override
    public void onMessage(ConsumerRecord<Integer, String> data) {
        LOG.info("==========kafka消费数据成功（日志开始）==========");
        LOG.info("----------topic:" + data.topic());
        LOG.info("----------partition:" + data.partition());
        LOG.info("----------key:" + data.key());
        LOG.info("----------value:" + data.value());
        LOG.info("----------ConsumerRecord<Integer, String>:" + data);
        LOG.info("~~~~~~~~~~kafka消费数据成功（日志结束）~~~~~~~~~~");
    }
}
