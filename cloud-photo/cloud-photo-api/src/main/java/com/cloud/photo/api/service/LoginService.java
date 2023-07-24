package com.cloud.photo.api.service;


import com.cloud.photo.common.bo.UserBo;
import com.cloud.photo.common.common.ResultBody;

/**
 * 登录业务
 * @author linzsh
 */
public interface LoginService {

    /**
     * 用户登录
     * @param bo 用户信息体
     * @return 登录结果
     */
    ResultBody login(UserBo bo);

    /**
     * 退出登录
     * @return 退出登录结果
     */
    ResultBody logout();

    /**
     * 获取用户信息
     * @return 用户信息
     */
    ResultBody getUserInfo();

}
