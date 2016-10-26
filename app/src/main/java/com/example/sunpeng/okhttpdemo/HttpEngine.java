package com.example.sunpeng.okhttpdemo;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
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
        builder.connectTimeout(5000, TimeUnit.MILLISECONDS);
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

    public <T> void invoke(@NonNull BaseRequest baseRequest, Class<T> responseClazz, HttpCallBack<T> httpCallBack){
        switch (baseRequest.getRequestType()){
            case RequestType.GET:
                doGet(baseRequest,responseClazz,httpCallBack);
                break;
            case RequestType.POST:
                doPost(baseRequest,responseClazz,httpCallBack);
                break;
            case RequestType.UPLOAD:
                break;
            case RequestType.DOWNLOAD:
                break;
            default:
                break;
        }
    }

    private  <T> void doGet(BaseRequest baseRequest ,final Class<T> clazz, final HttpCallBack callBack){
        HttpUrl.Builder urlBuilder = createHttpUrlBuilder(baseRequest);
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
                        Log.i("response",strResponse);
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

    private  <T> void doPost(BaseRequest baseRequest, final Class<T> clazz,final HttpCallBack<T> callBack){
        RequestBody requestBody = createRequestBody(baseRequest);
        Request request = new Request.Builder().url(baseRequest.getUrl()).post(requestBody).build();
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
                        Log.i("response",strResponse);
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

    private  <T> void download(BaseRequest baseRequest ,final Class<T> clazz, final HttpCallBack callBack){
        Request request = new Request.Builder().url(baseRequest.getUrl()).get().build();
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
                        Log.i("response",strResponse);
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

    private HttpUrl.Builder createHttpUrlBuilder(BaseRequest baseRequest) {
        HttpUrl.Builder builder = HttpUrl.parse(baseRequest.getUrl()).newBuilder();
        String strRequest = JSON.toJSONString(baseRequest);
        JSONTokener jsonTokener = new JSONTokener(strRequest);
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonTokener.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> iterator = jsonObject.keys();
        if(baseRequest.isEncode()){
            while (iterator.hasNext()) {
                String key = iterator.next();
                builder.addEncodedQueryParameter(key, jsonObject.optString(key));
            }
        }else{
            while (iterator.hasNext()) {
                String key = iterator.next();
                builder.addQueryParameter(key, jsonObject.optString(key));
            }
        }

        return builder;
    }

    private RequestBody createRequestBody(BaseRequest baseRequest) {
        RequestBody requestBody;
        String strRequest = JSON.toJSONString(baseRequest);
        FormBody.Builder formBody = new FormBody.Builder();
        JSONTokener jsonTokener = new JSONTokener(strRequest);
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonTokener.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> iterator = jsonObject.keys();
        if (baseRequest.isEncode()) {
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
}
