package com.unipad.brain.consult.entity;

/**
 * Created by gongkan on 2016/6/12.
 */
public class NewDetails {

    /**
     * 字段名称	类型	描述	默认值	备注
     id	string	资讯id
     title	string	资讯标题
     thumb	string	缩略图url
     text	string	资讯内容
     publishDate	string	发布时间		格式：yyyy-MM-dd

     follow_num	number	关注人数
     praise_num	number	点赞人数
     review_num	number	评论人数
     share_num	number	分享人数
     */

    /**资讯id*/
    private String id;
    /**资讯标题*/
    private String title;
    /**缩略图url*/
    private String pictureUrl;
    /**资讯简介*/
    private String brief;
    /**资讯发布时间		格式：yyyy-MM-dd*/
    private String publishDate;
    /**关注人数*/
    private int follow_num;



    /**评论内容 */
    private String content;

    private int review_num;

    // 0是为 点赞   1 是点赞过；
    private  boolean isLike;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String thumbUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(int follow_num) {
        this.follow_num = follow_num;
    }



    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getReview_num() {
        return review_num;
    }

    public void setReview_num(int review_num) {
        this.review_num = review_num;

    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
