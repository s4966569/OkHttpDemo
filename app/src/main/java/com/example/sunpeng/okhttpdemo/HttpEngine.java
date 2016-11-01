package com.example.sunpeng.okhttpdemo;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
//        builder.addNetworkInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request =chain.request();
//                if(request.method().equals("GET")){
//                    Log.i("request::"+request.tag(),request.toString());
//                }else if(request.method().equals("POST")){
//                    Log.i("request::"+request.tag(),bodyToString(request.body()));
//                }
//                Response response = chain.proceed(request);
//                return response;
//            }
//        });
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

    public <T> void invoke(@NonNull BaseRequest baseRequest, Class<T> responseClazz, HttpCallBack<T> httpCallBack) {
        switch (baseRequest.getRequestType()) {
            case RequestType.GET:
                doGet(baseRequest, responseClazz, httpCallBack);
                break;
            case RequestType.POST:
                doPost(baseRequest, responseClazz, httpCallBack);
                break;
            case RequestType.UPLOAD:
                doPost(baseRequest, responseClazz, httpCallBack);
                break;
            case RequestType.DOWNLOAD:
                download(baseRequest,httpCallBack);
                break;
            default:
                break;
        }
    }

    private <T> void doGet(BaseRequest baseRequest, final Class<T> clazz, final HttpCallBack callBack) {
        HttpUrl.Builder urlBuilder = createHttpUrlBuilder(baseRequest);
        Request request = new Request.Builder().tag(baseRequest.getTag()).url(urlBuilder.build()).get().build();
        Call call = mOkHttpClient.newCall(request);
//        Log.i("request::method="+request.method()+",tag="+request.tag(),"url="+request.url().toString());
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
                        Log.i("response::"+call.request().tag(), strResponse);
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

    private <T> void doPost(final BaseRequest baseRequest, final Class<T> clazz, final HttpCallBack<T> callBack) {
        RequestBody requestBody;
        if (baseRequest instanceof UploadRequest) {
            requestBody = createUploadRequestBody((UploadRequest) baseRequest);
        } else {
            requestBody = createRequestBody(baseRequest);
        }
        Request request = new Request.Builder().tag(baseRequest.getTag()).url(baseRequest.getUrl()).post(requestBody).build();
        Call call = mOkHttpClient.newCall(request);
//        Log.i("request::method="+request.method()+",tag="+request.tag(),"url="+request.url()+" params::"+bodyToString(request.body()));
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
                        String strResponse = response.body().string();
                        if(baseRequest.isDecrypt()){
                            strResponse = SysEncryptUtil.decryptDES(strResponse,"DES");
                        }
                        Log.i("response::"+call.request().tag(), strResponse);
                        final String finalStrResponse = strResponse;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (response.isSuccessful()) {
                                    T object;
                                    object = JSON.parseObject(finalStrResponse, clazz);
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

    private void download(BaseRequest baseRequest, final HttpCallBack callBack) {
        if (!(baseRequest instanceof DownloadRequest)) {
            throw new RuntimeException("request must be instanceof DownloadRequest");
        }
        DownloadRequest downloadRequest = (DownloadRequest) baseRequest;
        final ProgressListener progressListener = downloadRequest.getProgressListener();
        final File fileToSaveData = downloadRequest.getFileToSaveData();
        Request request = new Request.Builder().tag(baseRequest.getTag()).url(downloadRequest.getUrl()).get().build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            long contentLength = 0;
            long writtenBytes = 0;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (progressListener != null)
                        progressListener.onUpdate(contentLength, writtenBytes);
                }
            };

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
                    if (response.isSuccessful()) {
                        contentLength = response.body().contentLength();
                        InputStream is = response.body().byteStream();
                        FileOutputStream fos = null;
                        BufferedInputStream bis = null;
                        try {
                            fos = new FileOutputStream(fileToSaveData);
                            bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[2048];
                            int len;
                            while ((len = bis.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                                writtenBytes += len;
                                mHandler.post(runnable);
                            }
                        } catch (final FileNotFoundException e) {
                            e.printStackTrace();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onError(call, e.getMessage());
                                }
                            });
                            return;
                        } catch (final IOException e) {
                            e.printStackTrace();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onError(call, e.getMessage());
                                }
                            });
                            return;
                        } catch (final Exception e){
                            e.printStackTrace();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBack.onError(call, e.getMessage());
                                }
                            });
                            return;
                        }finally {
                            try {
                                fos.flush();
                                is.close();
                                fos.close();
                                bis.close();
                            } catch (final IOException e) {
                                e.printStackTrace();
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callBack.onError(call, e.getMessage());
                                    }
                                });
                                return;
                            }
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(call, fileToSaveData);

                            }
                        });
                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onError(call, response.code() + ":" + response.message());
                            }
                        });

                    }
                }
            }
        });
    }

    private HttpUrl.Builder createHttpUrlBuilder(BaseRequest baseRequest) {
        HttpUrl.Builder builder = HttpUrl.parse(baseRequest.getUrl()).newBuilder();
        String strRequest = JSON.toJSONString(baseRequest);
        JSONTokener jsonTokener = new JSONTokener(strRequest);
        JSONObject jsonObject ;
        try {
            jsonObject = (JSONObject) jsonTokener.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        Iterator<String> iterator = jsonObject.keys();
        if (baseRequest.isEncode()) {
            while (iterator.hasNext()) {
                String key = iterator.next();
                builder.addEncodedQueryParameter(key, jsonObject.optString(key));
            }
        } else {
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
            throw new RuntimeException(e.getMessage());
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

    private RequestBody createUploadRequestBody(UploadRequest uploadRequest) {
        FileUploadRequestBody fileUploadRequestBody = new FileUploadRequestBody(RequestBody.create(MediaType.parse("application/octet-stream"), uploadRequest.getFileToUpload()), uploadRequest.getProgressListener());
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        RequestBody formBody;
        String strRequest = JSON.toJSONString(uploadRequest);
        JSONTokener jsonTokener = new JSONTokener(strRequest);
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonTokener.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.addFormDataPart(key, jsonObject.optString(key));
        }
        if (TextUtils.isEmpty(uploadRequest.getFileKey())) {
            builder.addPart(fileUploadRequestBody);
        } else {
            builder.addFormDataPart(uploadRequest.getFileKey(), uploadRequest.getFileToUpload().getName(), fileUploadRequestBody);
        }
        formBody = builder.build();

        return formBody;
    }

    private static String bodyToString(RequestBody requestBody){
        try {
            okio.Buffer buffer = new okio.Buffer();
            requestBody.writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
            return  e.getMessage();
        }
    }
}
