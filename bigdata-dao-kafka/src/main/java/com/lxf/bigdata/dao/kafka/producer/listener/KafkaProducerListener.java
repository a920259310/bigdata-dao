package com.lxf.bigdata.dao.kafka.producer.listener;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;

public class KafkaProducerListener implements ProducerListener {

    protected final Logger LOG = LoggerFactory.getLogger(KafkaProducerListener.class);
    /**
     * 发送消息成功后调用
     */
    public void onSuccess(String topic, Integer partition, Object key,
                          Object value, RecordMetadata recordMetadata) {
        LOG.info("==========kafka发送数据成功（日志开始）==========");
        LOG.info("----------topic:"+topic);
        LOG.info("----------partition:"+partition);
        LOG.info("----------key:"+key);
        LOG.info("----------value:"+value);
        LOG.info("----------RecordMetadata:"+recordMetadata);
        LOG.info("~~~~~~~~~~kafka发送数据成功（日志结束）~~~~~~~~~~");
    }

    /**
     * 发送消息错误后调用
     */
    public void onError(String topic, Integer partition, Object key,
                        Object value, Exception exception) {
        LOG.info("==========kafka发送数据错误（日志开始）==========");
        LOG.info("----------topic:"+topic);
        LOG.info("----------partition:"+partition);
        LOG.info("----------key:"+key);
        LOG.info("----------value:"+value);
        LOG.info("----------Exception:"+exception);
        LOG.info("~~~~~~~~~~kafka发送数据错误（日志结束）~~~~~~~~~~");
        exception.printStackTrace();
    }

    /**
     * 方法返回值代表是否启动kafkaProducer监听器
     */
    public boolean isInterestedInSuccess() {
        LOG.info("///kafkaProducer监听器启动///");
        return true;
    }
}
