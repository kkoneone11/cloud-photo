package com.cloud.photo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author：kkoneone11
 * @name：CloudPhotoApiApplication
 * @Date：2023/7/11 22:05
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.cloud.photo.common.fegin"})
public class CloudPhotoApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudPhotoApiApplication.class,args);
    }
}
