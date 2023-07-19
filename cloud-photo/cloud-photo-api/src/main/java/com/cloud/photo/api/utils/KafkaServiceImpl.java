package com.cloud.photo.api.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author：kkoneone11
 * @name：KafkaServiceImpl
 * @Date：2023/7/9 14:20
 */

@Service
public class KafkaServiceImpl {
    private static final String FILE_IMAGE_TOPIC = "file-image-topic";

    @Autowired
    private KafkaTemplate<?,String> kafkaTemplate;

    /**
     * 发送kafka信息
     */
    public void send(String message){
        kafkaTemplate.send(FILE_IMAGE_TOPIC,message);
        System.out.println("send message :"+message);
    }


    /**
     * 消费FILE_IMAGE_TOPIC中的消息
     */
    @KafkaListener(topics = {"file-image-topic"})
    public void onMessage(ConsumerRecord<String,String> record){
        System.out.println("onMessage,key="+record.key()+",value="+record.value());
    }

}
