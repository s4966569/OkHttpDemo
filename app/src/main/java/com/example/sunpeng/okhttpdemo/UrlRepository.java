package com.example.sunpeng.okhttpdemo;

/**
 * Created by sunpeng on 2016/10/18.
 */

public class UrlRepository {
    private static final String TEST="0x01";
    private static final String REAL="0x02";

    private String mEnviroment = REAL;

    public static final String ACTION_EXAM = "exam";

    public static String getUrl(String actionName,String enviroment){
        String url=null;
        switch (actionName){
            case ACTION_EXAM:
                url = "";
                break;
        }
        return url;
    }

}
