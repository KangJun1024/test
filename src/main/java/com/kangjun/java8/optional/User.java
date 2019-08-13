package com.kangjun.java8.optional;

/**
 *  User类
 */
public class User {

    private String roleId;

    private String userName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User(String roleId) {
        this.roleId = roleId;
    }
}
