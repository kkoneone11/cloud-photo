package com.cloud.photo.api.feign;

import com.cloud.photo.api.common.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author：kkoneone11
 * @name：Api2UserFeginService
 * @Date：2023/7/12 10:53
 */


/**
 * @FeignClient 表明要去寻找的项目的名字
 */

@FeignClient("cloud-photo-users")
@RequestMapping("/users")
public interface Api2UserFegin {

    /**
     * 调用use服务下的接口，注意接口调用要对应到具体的方法
     * @param phone
     * @return
     */
    @GetMapping("/getUserInfo")
    ResultBody getUserInfo(@RequestParam(value = "phone") String phone);
}
