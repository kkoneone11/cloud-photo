package com.cloud.photo.api.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.cloud.photo.api.service.LoginService;
import com.cloud.photo.common.bo.UserBo;
import com.cloud.photo.common.common.CommonEnum;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.common.constant.CommonConstant;
import com.cloud.photo.common.fegin.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @author linzsh
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserFeignService userFeignService;


    /**
     * 登录
     * @param userBo
     * @return
     */
    @Override
    public ResultBody login(UserBo userBo) {
        //token表示已经登录过
        if(StpUtil.isLogin()){
            return ResultBody.success();
        }
        if(userBo == null){
            return ResultBody.error(CommonEnum.LOGIN_INFO_IS_NULL);
        }
        //1.管理员登录
        if(userBo.getRole().equals(CommonConstant.ADMIN)){
            //检查账密是否为空
            if(StrUtil.hasBlank(userBo.getUserName(),userBo.getPassword())){
                return ResultBody.error(CommonEnum.USERNAME_PASSWORD_ERROR);
            }
            //检查账密是否正确
            ResultBody checkResult = userFeignService.checkAdmin(userBo.getUserName(), userBo.getPassword());
            if(!checkResult.getCode().equals(CommonEnum.SUCCESS.getResultCode())){
                //账密不对
                return checkResult;
            }
            //正确则直接放行
            userBo.setPhone(userBo.getPhone());
            return doLogin(userBo,userBo.getPhone());
        }
        //2.普通用户登录
        //检查手机是否为空
        if(StrUtil.isBlank(userBo.getPhone())){
            return ResultBody.error(CommonEnum.PHONE_IS_NULL);
        }

        //判断手机号合不合法
        if (!Pattern.matches(CommonConstant.REGEX_MOBILE, userBo.getPhone())) {
            return ResultBody.error(CommonEnum.PHONE_IS_NOT_VALID);
        }

        //手机号
        String phone = userBo.getPhone();
        //如果没有登录 - 先检查下这个手机号是不是有记录
        ResultBody checkResult = userFeignService.checkPhone(phone);
        if (!checkResult.getCode().equals(CommonEnum.SUCCESS.getResultCode())) {
            //失败 - 表示用户是第一次登录 - 检验一下用户信息是否齐全
            if (userBo.getBirth() == null || StrUtil.isBlank(userBo.getDepartment()) || StrUtil.isBlank(userBo.getUserName())) {
                return ResultBody.error(CommonEnum.USER_INFO_NOT_INPUT);
            }
            //用户信息补全了 - 直接执行登录
        }
        //成功 - 表示有这个用户了 - 直接执行登录
        return doLogin(userBo, phone);
    }

    /**
     * 退出
     * @return
     */
    @Override
    public ResultBody logout() {
       if(StpUtil.isLogin()){
           String loginId = StpUtil.getLoginIdAsString();

           SaSession session = StpUtil.getSessionByLoginId(loginId);
           //清空session
           session.logout();
           StpUtil.logout();
       }
       return ResultBody.success();
    }

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public ResultBody getUserInfo() {
        if(StpUtil.isLogin()){
            //先拿出session
            String loginId = StpUtil.getLoginIdAsString();
            SaSession session = StpUtil.getSessionByLoginId(loginId);
            //session不为空则拿出用户消息
            if(session!=null){
                String userInfo = session.getModel(CommonConstant.USER_INFO, String.class);
                if(userInfo!=null){
                    return ResultBody.success(JSON.parseObject(userInfo,UserBo.class));
                }
            }
            //否则则用users的getUserInfo()方法
            ResultBody userInfo = userFeignService.getUserInfo(loginId);
            if(userInfo.getCode().equals(CommonEnum.SUCCESS.getResultCode())){
                //存进缓存
                SaSession saSession = StpUtil.getSessionByLoginId(loginId, true);
                saSession.set(CommonConstant.USER_INFO,JSON.toJSONString(userInfo.getData()));
            }
            return userInfo;
        }

        return ResultBody.error(CommonEnum.NO_LOGIN);
    }

    /**
     * 执行登录
     *
     * @param userBo  用户信息体
     * @param phone 手机号
     * @return 返回登录结果
     */
    private ResultBody doLogin(UserBo userBo, String phone) {
        ResultBody loginResult = userFeignService.login(userBo);
        //成功登录
        if(loginResult.getCode().equals(CommonEnum.SUCCESS.getResultCode())){
            //让satoken也执行成功登录的方法
            StpUtil.login(phone);
            if(StpUtil.isLogin()){
                //拿出token返回给前端
                String tokenValue = StpUtil.getTokenInfo().getTokenValue();
                return ResultBody.success(tokenValue);
            }
        }else{
            return loginResult;
        }
        //登录失败
        return loginResult;
    }
}
