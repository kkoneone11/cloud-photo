package com.cloud.photo.audit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author：kkoneone11
 * @name：CloudPhotoAuditApplication
 * @Date：2023/7/15 9:44
 */

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.cloud.photo"})
@MapperScan(basePackages = {"com.cloud.photo.audit.mapper"})
@ComponentScan({"com.cloud.photo"})
public class CloudPhotoAuditApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudPhotoAuditApplication.class,args);
    }
}
