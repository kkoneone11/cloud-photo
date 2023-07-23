package com.cloud.photo.image;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author：kkoneone11
 * @name：CloudPhotoImageApplication
 * @Date：2023/7/11 14:47
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = {"com.cloud.photo.image.mapper"})
@EnableFeignClients(basePackages = {"com.cloud.photo.common.fegin"})
public class CloudPhotoImageApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudPhotoImageApplication.class,args);
    }
}
