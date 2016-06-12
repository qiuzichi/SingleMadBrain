package com.unipad;

/**
 * Created by gongjiebin on 2016/6/8.
 * 描述：  实名认证， 实体
 */
public class AuthEntity {
    private  String sex; // 性别

    private String type; // 用户类别

    private String name; // 真实姓名

    private String identity; // 身份证号码

    private String id; // 用户id

    private String birthDate; // 出生日期

    private String idFrontUrl; // 身份证正面 图片地址

    private String idReverseUrl; // 身份证反面 图片地址

    private String rating_certificate1; // 等级证书

    private String rating_certificate2; // 等级证书2

    public AuthEntity(String type, String sex, String name, String id, String birthDate, String idFrontUrl, String idReverseUrl, String rating_certificate1, String rating_certificate2) {
        this.type = type;
        this.sex = sex;
        this.name = name;
        this.identity = id;
        this.birthDate = birthDate;
        this.idFrontUrl = idFrontUrl;
        this.idReverseUrl = idReverseUrl;
        this.rating_certificate1 = rating_certificate1;
        this.rating_certificate2 = rating_certificate2;
    }

    public AuthEntity() {
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdFrontUrl() {
        return idFrontUrl;
    }

    public void setIdFrontUrl(String idFrontUrl) {
        this.idFrontUrl = idFrontUrl;
    }

    public String getIdReverseUrl() {
        return idReverseUrl;
    }

    public void setIdReverseUrl(String idReverseUrl) {
        this.idReverseUrl = idReverseUrl;
    }

    public String getRating_certificate1() {
        return rating_certificate1;
    }

    public void setRating_certificate1(String rating_certificate1) {
        this.rating_certificate1 = rating_certificate1;
    }

    public String getRating_certificate2() {
        return rating_certificate2;
    }

    public void setRating_certificate2(String rating_certificate2) {
        this.rating_certificate2 = rating_certificate2;
    }
}
