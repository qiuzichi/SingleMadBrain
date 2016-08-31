package com.unipad.singlebrain.personal.bean;

/**
 * Created by Wbj on 2016/5/3.
 */
public class Pwd {
    private String originPwd;
    private String newPwd;
    private String repeatNewPwd;

    public Pwd(String originPwd, String newPwd, String repeatNewPwd) {
        this.originPwd = originPwd;
        this.newPwd = newPwd;
        this.repeatNewPwd = repeatNewPwd;
    }

    public String getOriginPwd() {
        return originPwd;
    }

    public void setOriginPwd(String originPwd) {
        this.originPwd = originPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getRepeatNewPwd() {
        return repeatNewPwd;
    }

    public void setRepeatNewPwd(String repeatNewPwd) {
        this.repeatNewPwd = repeatNewPwd;
    }
}
