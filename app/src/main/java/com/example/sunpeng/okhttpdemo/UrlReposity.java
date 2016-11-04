package com.example.sunpeng.okhttpdemo;

import java.security.PublicKey;

/**
 * Created by sunpeng on 16-11-3.
 */

public class UrlReposity {

    public static final boolean isTestEnvironment = false;

    public static final String SERVER_REAL = "http://mobile.yanxiu.com/v20/";
    public static final String SERVER_TEST = "http://mobile.yanxiu.com/test/";

    //考核
    public static final String ACTION_EXAM="api/guopei/examine";
    //上传头像
    public static final String ACTION_UPLOAD_HEADER="api/resource/uploadheader";
    //登录
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_LOGIN_REAL = "http://u.yanxiu.com/login.json";
    public static final String ACTION_LOGIN_TEST = "http://u.yanxiu.com/test/login.json";



    public static String getAbsoluteUrl(String actionName){
        String url,url_prefix;
        if(isTestEnvironment){
            url_prefix = SERVER_TEST;
        }else{
            url_prefix = SERVER_REAL;
        }
        url = url_prefix+actionName;
        return url;
    }

    public static String getSpecificUrl(String actionName){
        String url = "";
        switch (actionName){
            case ACTION_LOGIN:
                if(isTestEnvironment){
                    url = ACTION_LOGIN_TEST;
                }else{
                    url = ACTION_LOGIN_REAL;
                }
                break;
            default:
                break;
        }

        return url;
    }


}
