package com.unipad.brain.personal.bean;

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

}
