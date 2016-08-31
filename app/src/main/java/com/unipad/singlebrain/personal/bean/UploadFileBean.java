package com.unipad.singlebrain.personal.bean;

/**
 * Created by gongjiebin on 2016/6/13.
 */
public class UploadFileBean {
    // {"data":"\\api\\20160613\\BE46D5F8AF834C6298C65E17C4009CD3.jpg","ret_code":"0"}
    private String path;

    private int ret_code;

    public UploadFileBean() {
    }

    public UploadFileBean(String path, int ret_code) {
        this.path = path;
        this.ret_code = ret_code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }
}
