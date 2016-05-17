package com.unipad.brain.portraits.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by gongkan on 2016/4/11.人名肖像的实体类
 */
@Table(name = "person")
public class Person {
    @Column(name = "id", isId = true)
    private int id;
    /**
     * 人物的姓
     */
    @Column(name = "firstName")
    private String firstName;

    /**
     * 人物的名
     */
    @Column(name = "lastName")
    private String lastName;

    /**人物的国籍*/
    // @Column(name = "nationality")
    //private String  nationality;

    /**
     * 人物的图像的地址
     */
    @Column(name = "path")
    private String headPortraitPath;

    //@Column(name = "local")

    // private String  local ;
    /**
     * 答题的姓氏
     */
    private String answerFirstName = "";

    /**
     * 答题的名
     */
    private String answerLastName = "";


    public Person(String firstName, String lastName, String headPortraitPath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.headPortraitPath = headPortraitPath;
    }

    public Person(int id, String firstName, String lastName, String headPortraitPath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.headPortraitPath = headPortraitPath;
        this.id = id;
    }

    public void setAnswerFirstName(String answerFirstName) {
        this.answerFirstName = answerFirstName;
    }

    public String getAnswerLastName() {
        return answerLastName;
    }

    public void setAnswerLastName(String answerLastName) {
        this.answerLastName = answerLastName;
    }


    public String getAnswerFirstName() {
        return answerFirstName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getHeadPortraitPath() {
        return headPortraitPath;
    }

    public void setHeadPortraitPath(String headPortraitPath) {
        this.headPortraitPath = headPortraitPath;
    }
}
