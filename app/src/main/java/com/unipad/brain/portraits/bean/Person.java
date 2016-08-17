package com.unipad.brain.portraits.bean;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
/**
 * Created by gongkan on 2016/4/11.人名肖像的实体类
 */
@Table(name = "un_person")
public class Person {
     @Column(name = "_id", isId = true)
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

    private String content;//保留字段

    public void setTag(String tag) {
        this.tag = tag;
    }

    private String tag;//保留字段

    public Person(){

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public String getHeadPortraitPath() {
        return headPortraitPath;
    }

    public void setHeadPortraitPath(String headPortraitPath) {
        this.headPortraitPath = headPortraitPath;
    }

    public boolean isAnswerRight() {
        if (lastName.equals(answerLastName) && firstName.equals(answerFirstName)){
            return true;
        }
        return false;
    }

    public float getScore() {
        float score = 0;
        if (lastName.equals(answerLastName)){
            score += 1;
        }
        if (firstName.equals(answerFirstName)){
            score += 1;
        }
        return score;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return  "id = " + id   + "   tag =" +  tag + " content=" + content +"^"+firstName+"^"+lastName+"^"+answerFirstName+"^"+answerLastName;
    }

    public String getTag() {
        return tag;
    }
}
