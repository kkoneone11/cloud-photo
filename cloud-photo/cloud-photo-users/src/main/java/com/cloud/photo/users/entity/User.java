package com.cloud.photo.users.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author ltao
 * @since 2023-07-09
 */
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户所属地
     */
    private String department;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户创建时间
     */
    private LocalDateTime createTime;

    /**
     * 用户最后登录时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户登录次数
     */
    private Integer loginCount;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 用户出生年月日
     */
    private LocalDate birth;

    private Integer newColumn;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Integer getNewColumn() {
        return newColumn;
    }

    public void setNewColumn(Integer newColumn) {
        this.newColumn = newColumn;
    }

    @Override
    public String toString() {
        return "User{" +
            "userId = " + userId +
            ", userName = " + userName +
            ", department = " + department +
            ", phone = " + phone +
            ", password = " + password +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
            ", loginCount = " + loginCount +
            ", role = " + role +
            ", birth = " + birth +
            ", newColumn = " + newColumn +
        "}";
    }
}
