package com.cloud.photo.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author：kkoneone11
 * @name：CloudPhotoImageApplication
 * @Date：2023/7/11 14:47
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CloudPhotoImageApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudPhotoImageApplication.class,args);
    }
}
