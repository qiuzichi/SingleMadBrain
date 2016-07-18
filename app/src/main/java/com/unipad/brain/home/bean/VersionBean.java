package com.unipad.brain.home.bean;

/**
 * Created by hasee on 2016/7/15.
 */
public class VersionBean {
    //版本号；
    private String version;
    //下载的路径
    private String path;
    //新版本的描述内容
    private String infoDescription;


    //更新日期；
    private String updateTime;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getInfoDescription() {
        return infoDescription;
    }

    public void setInfoDescription(String infoDescription) {
        this.infoDescription = infoDescription;
    }
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
