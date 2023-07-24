package com.cloud.photo.api.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.cloud.photo.common.bo.UserBo;
import com.cloud.photo.common.constant.CommonConstant;


/**
 * 自定义工具
 * @author linzsh
 */
public class MyUtils {

    /**
     * 从session中获取用户信息
     * @return 用户信息 若用户未登录或登录信息过期，返回null
     */
    public static UserBo getUserInfo(){
        if (StpUtil.isLogin()){
            String userInfo = StpUtil.getSessionByLoginId(StpUtil.getLoginId()).getModel(CommonConstant.USER_INFO, String.class);
            return JSON.parseObject(userInfo, UserBo.class);
        }else {
            return null;
        }
    }
}
