package com.cloud.photo.image.consumer;

import com.alibaba.fastjson.JSONObject;
import com.cloud.photo.image.service.IFileResizeIconService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Author：kkoneone11
 * @name：ImageConsumer
 * @Date：2023/7/22 18:24
 */

@Component
public class ImageConsumer {
    @Autowired
    IFileResizeIconService iFileResizeIconService;

    @KafkaListener(topics = {"file_image_topic"})
    public void onMessage(ConsumerRecord<String,Object> record){
        //打印消费的topic和信息
        System.out.println("消费:" + record.topic() + "-" + record.partition()+"-"+record.value());
        //取出record里的信息转成JSON格式
        Object value = record.value();
        JSONObject jsonObject = JSONObject.parseObject(value.toString());
        String userFileId = jsonObject.getString("userFileId");
        String storageObjectId = jsonObject.getString("storageObjectId");
        String fileName = jsonObject.getString("fileName");

        iFileResizeIconService.imageThumbnailAndMediaInfo(storageObjectId,fileName);

    }
}
