package com.example.sunpeng.okhttpdemo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by sunpeng on 2016/10/17.
 */

public class BaseRequest implements Serializable {
    @JSONField(serialize = false)
    private String url;                    //请求地址
    @JSONField(serialize = false)
    private String requestType = RequestType.GET;    //请求方式,默认为Get
    @JSONField(serialize = false)
    private String tag;                    //请求标记
    @JSONField(serialize = false)
    private boolean isEncode = false;      //是否需要URLEncoder进行编码
    @JSONField(serialize = false)
    private boolean isEncrypt = false;      //请求参数是否需要加密
    @JSONField(serialize = false)
    private boolean isDecrypt = false;      //返回数据是否需要解密
    @JSONField(serialize = false)
    private boolean isUseMockData = false;  //是否使用mock模拟数据
    @JSONField(serialize = false)
    private String mockFileName;            //放在Assets目录下面的模拟数据文件的名字

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isEncode() {
        return isEncode;
    }

    public void setEncode(boolean encode) {
        isEncode = encode;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public void setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
    }

    public boolean isDecrypt() {
        return isDecrypt;
    }

    public void setDecrypt(boolean decrypt) {
        isDecrypt = decrypt;
    }

    public boolean isUseMockData() {
        return isUseMockData;
    }

    public void setUseMockData(boolean useMockData) {
        isUseMockData = useMockData;
    }

    public String getMockFileName() {
        return mockFileName;
    }

    public void setMockFileName(String mockFileName) {
        this.mockFileName = mockFileName;
    }
}
