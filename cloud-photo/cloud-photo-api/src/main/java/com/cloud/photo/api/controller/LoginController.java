package com.cloud.photo.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.cloud.photo.api.service.LoginService;
import com.cloud.photo.common.bo.UserBo;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.fegin.Cloud2TransService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * api控制器
 *
 * @author 11446
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private Cloud2TransService cloud2TransService;

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResultBody login(@RequestBody UserBo userBo){return loginService.login(userBo);}


}
