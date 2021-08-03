package com.bytedance.toutiao_demo.entity;

import java.util.List;

public class News {
    //新闻内容
    private String content;
    //阅读数
    private int read_count;
    //评论数
    private int comment_count;
    //点赞数
    private int digg_count;
    //展示数
    private int display_count;
    //发表时间戳
    private String publish_time;
    //用户
    private User user;
    //新闻图片列表(ugc剪切)
    private List<Image> ugc_cut_image_list;
    //新闻图片原图
    private List<Image> origin_image_list;
    //转发情况
    private ForwardInfo forward_info;





    public String getContent() {
        return content;
    }

    public int getRead_count() {
        return read_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public int getDigg_count() {
        return digg_count;
    }

    public int getDisplay_count() {
        return display_count;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public User getUser() {
        return user;
    }

    public List<Image> getUgc_cut_image_list() {
        return ugc_cut_image_list;
    }

    public List<Image> getOrigin_image_list() {
        return origin_image_list;
    }

    public ForwardInfo getForward_info() {
        return forward_info;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }

    public void setDisplay_count(int display_count) {
        this.display_count = display_count;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUgc_cut_image_list(List<Image> ugc_cut_image_list) {
        this.ugc_cut_image_list = ugc_cut_image_list;
    }

    public void setOrigin_image_list(List<Image> origin_image_list) {
        this.origin_image_list = origin_image_list;
    }

    public void setForward_info(ForwardInfo forward_info) {
        this.forward_info = forward_info;
    }
}
