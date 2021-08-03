package com.bytedance.toutiao_demo.entity;

public class Comment {
    //评论内容
    private String text;
    //评论回复数
    private int reply_count;
    //评论点赞数
    private int digg_count;
    //评论发表时间戳
    private String create_time;
    //评论用户名
    private String user_name;
    //评论用户头像链接
    private String user_profile_image_url;

    public String getText() {
        return text;
    }

    public int getReply_count() {
        return reply_count;
    }

    public int getDigg_count() {
        return digg_count;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_profile_image_url() {
        return user_profile_image_url;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_profile_image_url(String user_profile_image_url) {
        this.user_profile_image_url = user_profile_image_url;
    }
}
