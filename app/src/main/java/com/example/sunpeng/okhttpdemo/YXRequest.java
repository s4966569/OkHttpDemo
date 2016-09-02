package com.example.sunpeng.okhttpdemo;

import java.io.Serializable;

/**
 * Created by sunpeng on 2016/9/2.
 */
public class YXRequest implements Serializable {
    private String token;
    private String pid;
    private String w;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }
}
