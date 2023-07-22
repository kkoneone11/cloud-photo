package com.cloud.photo.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * 用户表
 * @author linzsh
 */
@Data
public class UserBo {

    private String userId;
    private String userName;
    private String department;
    private Date birth;
    private String phone;
    private String password;
    private Date createTime;
    private Date updateTime;
    private Integer loginCount;
    private String role;
}
