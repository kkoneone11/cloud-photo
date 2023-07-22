package com.cloud.photo.trans;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author：kkoneone11
 * @name：CloudPhotoTransApplication
 * @Date：2023/7/20 9:41
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.cloud.photo.trans.mapper"})
public class CloudPhotoTransApplication {
}
