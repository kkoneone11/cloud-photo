package com.cloud.photo.api.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author：kkoneone11
 * @name：KafkaSeriveTest
 * @Date：2023/7/9 14:44
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaSeriveTest {
    @Autowired
    private KafkaServiceImpl kafkaService;

    @Test
    public void send(){
        kafkaService.send("666");
    }
}
