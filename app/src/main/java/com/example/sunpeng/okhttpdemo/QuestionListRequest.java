package com.example.sunpeng.okhttpdemo;

import java.io.Serializable;

/**
 * Created by sunpeng on 2016/9/4.
 */
public class QuestionListRequest implements Serializable {
    private String paperId;
    private String osType;
    private String pcode;
    private String token;
    private String version;

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
