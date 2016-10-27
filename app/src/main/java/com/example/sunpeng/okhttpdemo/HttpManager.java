package com.example.sunpeng.okhttpdemo;

import android.support.annotation.NonNull;

/**
 * Created by sunpeng on 2016/10/21.
 */

public class HttpManager {
    public static boolean isUseMockData = false;
    private static HttpManager mHttpManager=null;
    public static HttpManager getInstance(){
        if(mHttpManager==null){
            synchronized (HttpManager.class){
                if(mHttpManager==null)
                    mHttpManager = new HttpManager();
            }
        }
        return mHttpManager;
    }

    public <T> void invoke(@NonNull BaseRequest baseRequest, Class<T> responseClazz, HttpCallBack<T> httpCallBack){
        if(baseRequest.isUseMockData() && isUseMockData){
            MockEngine.getInstance().invoke(baseRequest,responseClazz,httpCallBack);
        }else{
            HttpEngine.getInstance().invoke(baseRequest,responseClazz,httpCallBack);
        }
    }
}
