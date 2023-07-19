package com.cloud.photo.users.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.photo.users.common.CommonEnum;
import com.cloud.photo.users.common.ResultBody;
import com.cloud.photo.users.entity.User;
import com.cloud.photo.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ltao
 * @since 2023-07-09
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获得用户信息，根据用户手机获取用户信息
     * @param phone
     * @return resultBody
     */
    @GetMapping("/getUserInfo")
    public ResultBody getUserInfo(@RequestParam(value = "phone") String phone){
        log.info("getUserInfo-phone"+ phone + ",start!");

        User user = userService.getOne(new QueryWrapper<User>().eq("phone",phone));

        log.info("getUserInfo()-phone=" + phone + ",user=" + user);

        ResultBody resultBody = (user == null ) ? ResultBody.error(CommonEnum.USER_IS_NULL) : ResultBody.success(user);

        log.info("getUserInfo()-phone=" + phone + ",resultBody=" + resultBody);

        return resultBody;

    }

    /**
     * 添加用户
     * @param user 需要一个请求体,发送请求的时候可以根据User来构造一个
     * @return
     */
    @PostMapping("/addUser")
    public ResultBody addUser(@RequestBody User user){
        boolean result = userService.save(user);
        return result ? ResultBody.success() : ResultBody.error(CommonEnum.SAVE_ERROR);
    }
}
