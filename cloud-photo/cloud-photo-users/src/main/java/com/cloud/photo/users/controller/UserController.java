package com.cloud.photo.users.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.photo.common.bo.UserBo;
import com.cloud.photo.common.constant.CommonConstant;
import com.cloud.photo.users.common.CommonEnum;
import com.cloud.photo.users.common.ResultBody;
import com.cloud.photo.users.entity.User;
import com.cloud.photo.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
     * 登录
     * @param userBo
     * @return
     */
    @PostMapping("/login")
    public ResultBody login(@RequestBody UserBo userBo){
        String phone = userBo.getPhone();

        //查询用户
        User user = userService.getOne(new QueryWrapper<User>().eq("phone", phone), false);

        //1.用户不存在则生成
        if(user == null){
            //组装
            user = new User();
            //复制
            BeanUtils.copyProperties(userBo, user);
            //自定义一个用户ID
            user.setUserId(RandomUtil.randomString(9));
            user.setCreateTime(DateUtil.date());
            user.setUpdateTime(DateUtil.date());
            user.setLoginCount(0);
            //普通用户
            user.setRole("user");

        }else{
        //2.存在则更新状态即可
            user.setUpdateTime(DateUtil.date());
            user.setLoginCount(user.getLoginCount()+1);
        }
        //入库
        boolean saveOrUpdate = userService.saveOrUpdate(user);
        return saveOrUpdate ? ResultBody.success() : ResultBody.error(CommonEnum.LOGIN_FAIL);

    }

    /**
     * 查询用户是否存在
     *
     * @param phone 手机号
     * @return 查询结果
     */
    @GetMapping("/checkPhone")
    public ResultBody checkPhone(@RequestParam(value = "phone") String phone) {
        User userEntity = userService.getOne(new QueryWrapper<User>().eq("phone", phone));
        return userEntity == null ? ResultBody.error(CommonEnum.USER_IS_NULL) : ResultBody.success();
    }

    /**
     * 检查管理员登录
     * @param userName
     * @param password
     * @return
     */
    @GetMapping("/checkAdmin")
    public ResultBody checkAdmin(@RequestParam(value = "userName") String userName,
                                 @RequestParam(value = "password") String password){
        User user = userService.getOne(new QueryWrapper<User>()
                .eq("user_name", userName)
                .eq("password", password)
                .eq("role", CommonConstant.ADMIN));

        return user == null ? ResultBody.error(CommonEnum.USERNAME_PASSWORD_ERROR) : ResultBody.success();
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
