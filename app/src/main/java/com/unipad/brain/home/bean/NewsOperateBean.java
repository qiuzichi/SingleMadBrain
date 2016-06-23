package com.unipad.brain.home.bean;

/**
 * Created by jiangLu on 2016/6/22.
 */
public class NewsOperateBean {
    //资讯的id
    private String articleId;
    //用户id号码
    private String userId;
    //用户类型
    private String roleId;
    //method 是用户的操作那个tag
    private String method;

    //操作的方式;
    private String methodType;

    //评论内容
    private String content;
    //评论类型
    private int contentType;

    public String getArticleId() {
        return articleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getMethod() {
        return method;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getMethodType() {
        return methodType;
    }

    public String getContent() {
        return content;
    }

    public int getContentType() {
        return contentType;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
}
