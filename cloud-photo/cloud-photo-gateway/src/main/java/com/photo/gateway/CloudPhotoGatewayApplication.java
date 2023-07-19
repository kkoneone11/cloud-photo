package com.photo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author：kkoneone11
 * @name：CloudPhotoGatewayApplication
 * @Date：2023/7/11 22:08
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CloudPhotoGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudPhotoGatewayApplication.class,args);
    }
}
