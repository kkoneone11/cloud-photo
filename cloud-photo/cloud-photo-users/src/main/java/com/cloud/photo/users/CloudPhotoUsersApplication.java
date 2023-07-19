package com.cloud.photo.users;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author：kkoneone11
 * @name：CloudPhotoUsersApplication
 * @Date：2023/7/11 18:50
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = {"com.cloud.photo.users.mapper"})
public class CloudPhotoUsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudPhotoUsersApplication.class,args);
    }
}
