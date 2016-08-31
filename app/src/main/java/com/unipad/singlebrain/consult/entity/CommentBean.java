package com.unipad.singlebrain.consult.entity;

/**
 * Created by jiangLu on 2016/6/30.
 */
public class CommentBean {
    //评论id
    private String id;
    //用户id
    private String userId;
    //评论用户名
    private String userName;
    //评论内容
    private String content;
    //评论日期；格式：yyyy-MM-dd
    private String createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
