package com.bytedance.toutiao_demo.entity;

public class User {
    //用户姓名
    private String name;
    //用户别名/简介
    private String remark_name;
    //用户头像链接
    private String avatar_url;


    public String getName() {
        return name;
    }

    public String getRemark_name() {
        return remark_name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemark_name(String remark_name) {
        this.remark_name = remark_name;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
