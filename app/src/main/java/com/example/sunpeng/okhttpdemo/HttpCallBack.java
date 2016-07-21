package com.example.sunpeng.okhttpdemo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sunpeng on 2016/7/7.
 * 此类定义：
 */
public interface HttpCallBack {
    void onFailure(Call call, IOException e);
    void onResponse(Call call, Response response);
}
