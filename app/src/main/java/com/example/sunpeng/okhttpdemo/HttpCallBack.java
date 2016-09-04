package com.example.sunpeng.okhttpdemo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sunpeng on 2016/7/7.
 */
public interface HttpCallBack<T> {
    void onError(Call call, String errorMsg);
    void onSuccess(Call call, T t);
}
