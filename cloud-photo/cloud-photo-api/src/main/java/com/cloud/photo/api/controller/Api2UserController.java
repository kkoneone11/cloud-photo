package com.cloud.photo.api.controller;

import com.cloud.photo.api.common.ResultBody;
import com.cloud.photo.api.feign.Api2UserFegin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：kkoneone11
 * @name：Api2UserController
 * @Date：2023/7/12 10:53
 */

/**
 * 通过"/api/getUserInfo"路径去访问Api2UserController
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class Api2UserController {
    @Autowired
    private Api2UserFegin api2UserFegin;


    @GetMapping("/getUserInfo")
    public ResultBody getUserInfo(@RequestParam(value = "phone") String phone){
        log.info("getUserInfo() - phone =" + phone + ",start!");
        //使用fegin调用
        ResultBody resultBody = api2UserFegin.getUserInfo(phone);
        log.info("getUserInfo() - phone" + phone + ",resultBody" + resultBody);
        return resultBody;
    }
}
