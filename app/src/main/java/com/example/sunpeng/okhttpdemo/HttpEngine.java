package com.example.sunpeng.okhttpdemo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sunpeng on 2016/9/2.
 */
public class HttpEngine {
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private static volatile HttpEngine mInstance;

    private HttpEngine() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(2, TimeUnit.SECONDS);
//        mOkHttpClient = new OkHttpClient();
        mOkHttpClient = builder.build();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static HttpEngine getInstance() {
        if (mInstance == null) {
            synchronized (HttpEngine.class) {
                if (mInstance == null)
                    mInstance = new HttpEngine();
            }
        }
        return mInstance;
    }


    private RequestBody createRequestBody(Object object) {
        return createRequestBody(object, false);
    }

    private RequestBody createRequestBody(Object object, boolean isEncode) {
        RequestBody requestBody;
        String strRequest = JSON.toJSONString(object);
        FormBody.Builder formBody = new FormBody.Builder();
        JSONTokener jsonTokener = new JSONTokener(strRequest);
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonTokener.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> iterator = jsonObject.keys();
        if (isEncode) {
            while (iterator.hasNext()) {
                String key = iterator.next();
                formBody.addEncoded(key, jsonObject.optString(key));
            }
        } else {
            while (iterator.hasNext()) {
                String key = iterator.next();
                formBody.add(key, jsonObject.optString(key));
            }
        }

        requestBody = formBody.build();
        return requestBody;
    }

    private HttpUrl.Builder createHttpUrlBuilder(String url, Object object) {
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        String strRequest = JSON.toJSONString(object);
        JSONTokener jsonTokener = new JSONTokener(strRequest);
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonTokener.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.addQueryParameter(key, jsonObject.optString(key));
        }
        return builder;
    }

    public <T> void doPost(String url, Object requestObj, Class<T> responseClazz, final HttpCallBack<T> callBack) {
        doPost(url, requestObj, responseClazz, false, callBack);
    }

    public <T> void doPost(String url, Object requestObj, final Class<T> clazz, final boolean isEncode, final HttpCallBack<T> callBack){
        RequestBody requestBody = createRequestBody(requestObj, isEncode);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                if (callBack != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(call, e.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                if (callBack != null) {
                    try {
                        final String strResponse = response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (response.isSuccessful()) {
                                    T object;
                                    if (isEncode) {
                                        object = JSON.parseObject(SysEncryptUtil.decryptDES(strResponse, "DES"), clazz);
                                        callBack.onSuccess(call, object);
                                    } else {
                                        object = JSON.parseObject(strResponse, clazz);
                                        Log.i("success", strResponse);
                                        callBack.onSuccess(call, object);
                                    }
                                } else {
                                    callBack.onError(call, response.code() + ":" + response.message());
                                }
                            }
                        });
                    } catch (Exception e) {
                        callBack.onError(call, e.getMessage());
                    }
                }
            }
        });
    }

    public <T> void doGet(String url, Object requestObj, final Class<T> clazz, final HttpCallBack callBack){
        HttpUrl.Builder urlBuilder = createHttpUrlBuilder(url, requestObj);
        Request request = new Request.Builder().url(urlBuilder.build()).get().build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                if (callBack != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(call, e.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                if (callBack != null) {
                    try {
                        final String strResponse = response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (response.isSuccessful()) {
                                    T object;
                                    object = JSON.parseObject(strResponse, clazz);
                                    callBack.onSuccess(call, object);
                                } else {
                                    callBack.onError(call, response.code() + ":" + response.message());
                                }
                            }
                        });
                    } catch (Exception e) {
                        callBack.onError(call, e.getMessage());
                    }
                }
            }
        });
    }
}
