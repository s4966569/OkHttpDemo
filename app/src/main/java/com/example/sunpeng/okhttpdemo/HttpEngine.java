package com.example.sunpeng.okhttpdemo;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.Iterator;

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

    private HttpEngine(){
        mOkHttpClient=new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static HttpEngine getInstance(){
        if(mInstance==null){
            synchronized (HttpEngine.class){
                if(mInstance==null)
                    mInstance=new HttpEngine();
            }
        }
        return mInstance;
    }


    private RequestBody createRequestBody(Object object){
        RequestBody requestBody=null;
        String strRequest= JSON.toJSONString(object);
        FormBody.Builder formBody = new FormBody.Builder();
        JSONTokener jsonTokener = new JSONTokener(strRequest);
        JSONObject jsonObject=null;
        try {
            jsonObject = (JSONObject) jsonTokener.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()){
            String key = iterator.next();
            formBody.add(key,jsonObject.optString(key));
        }
        requestBody = formBody.build();
        return requestBody;
    }

    private HttpUrl.Builder createHttpUrlBuilder(String url,Object object){
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        String strRequest= JSON.toJSONString(object);
        JSONTokener jsonTokener = new JSONTokener(strRequest);
        JSONObject jsonObject=null;
        try {
            jsonObject = (JSONObject) jsonTokener.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()){
            String key = iterator.next();
            builder.addQueryParameter(key,jsonObject.optString(key));
        }
        return builder;
    }

    /**
     * 异步post请求
     * @param url
     * @param requestObj
     * @param callBack
     * @throws IOException
     */
    public void doPost(String url,Object requestObj,final HttpCallBack callBack) throws IOException {
        RequestBody requestBody = createRequestBody(requestObj);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(callBack!=null)
                            callBack.onError(call,e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    String strResponse = response.body().string();
                    @Override
                    public void run() {
                        if(callBack!=null) {
                            if(response.code()>=200 && response.code() <300){
                                callBack.onSuccess(call, strResponse);
                            }else {
                                callBack.onError(call,response.code()+":"+response.message());
                            }
                        }
                    }
                });
            }
        });
    }

    public void doGet(String url,Object requestObj,final HttpCallBack callBack) throws IOException {
        HttpUrl.Builder urlBuilder = createHttpUrlBuilder(url,requestObj);
        Request request = new Request.Builder().url(urlBuilder.build()).get().build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(callBack!=null)
                            callBack.onError(call,e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    String strResponse = response.body().string();
                    @Override
                    public void run() {
                        if(callBack!=null) {
                            if(response.isSuccessful()){
                                callBack.onSuccess(call, strResponse);
                            }else {
                                callBack.onError(call,response.code()+":"+response.message());
                            }
                        }
                    }
                });
            }
        });
    }
}
