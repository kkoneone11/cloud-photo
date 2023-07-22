package com.cloud.photo.common.fegin;


import com.cloud.photo.common.bo.UserBo;
import com.cloud.photo.common.common.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 */
@Service
@FeignClient("cloud-photo-users")
public interface UserFeignService {

    /**
     * feign接口：访问用户服务获取用户信息
     * @param phone 手机号
     * @return 用户信息
     */
    @GetMapping("/users/getUserInfo")
    ResultBody getUserInfo(@RequestParam(value = "phone") String phone);

    /**
     * 执行用户登录
     * @param bo 用户登录信息
     * @return 登录结果
     */
    @PostMapping("/users/login")
    ResultBody login(UserBo bo);

    /**
     * 查询用户是否存在
     * @param phone 手机号
     * @return 查询结果
     */
    @GetMapping("/users/checkPhone")
    ResultBody checkPhone(@RequestParam(value = "phone") String phone);

    /**
     * 检查账密
     * @param userName 账号
     * @param password 密码
     * @return 查询结果
     */
    @GetMapping("/users/checkAdmin")
    ResultBody checkAdmin(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password);

}
