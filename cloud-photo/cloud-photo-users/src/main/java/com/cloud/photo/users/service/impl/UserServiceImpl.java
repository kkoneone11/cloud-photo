package com.cloud.photo.users.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.photo.users.entity.User;
import com.cloud.photo.users.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.photo.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ltao
 * @since 2023-07-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public User getUserInfoById(Long userId){
        QueryWrapper queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("USER_ID",userId);
        return this.getOne(queryWrapper);
    }
}
