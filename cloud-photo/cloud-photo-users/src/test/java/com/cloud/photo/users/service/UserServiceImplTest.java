package com.cloud.photo.users.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.photo.users.entity.User;
import com.cloud.photo.users.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author：kkoneone11
 * @name：UserServiceImplTest
 * @Date：2023/7/9 17:32
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void insert(){
        User user = new User();
        user.setUserId("88888");
        user.setUserName("张三");
        userService.save(user);
    }

    @Test
    public void getOne(){
        User user = userService.getUserInfoById(88888L);
        System.out.println("================"+user.toString());
    }

    @Test
    public void getList(){
        QueryWrapper<User> qw = new QueryWrapper<User>();
        Page<User> page = new Page<>(1,2);
        IPage<User> userFilePage = userService.page(page,qw.orderByDesc("user_id","create_time"));
        List<User> users = userFilePage.getRecords();
        for(User user:users){
            System.out.println(user);
        }
    }



}
