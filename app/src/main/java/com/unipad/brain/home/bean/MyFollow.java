package com.unipad.brain.home.bean;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
public class MyFollow {
    /*比赛列表*/
    private String match;
    /*资讯列表*/
    private String news;
    /*比赛列表*/
    private String name;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getMatch(){
        return match;
    }
    public void setMatch(String match){
        this.match=match;
    }
    public String getNews(){
        return news;
    }
    public void setNews(String news) {
        this.news = news;
    }

}
