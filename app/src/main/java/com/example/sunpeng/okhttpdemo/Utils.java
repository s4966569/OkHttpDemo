package com.example.sunpeng.okhttpdemo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sunpeng on 16-10-28.
 */

public class Utils {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private void uploadPortrait(File file,String url) {
        OkHttpClient httpClient = new OkHttpClient();
        FileUploadRequestBody fileUploadRequestBody = new FileUploadRequestBody(RequestBody.create(MediaType.parse("application/octet-stream"), file), progressListener);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody formBody = builder.setType(MultipartBody.FORM)
                .addFormDataPart("token", "5372dd841e2ca489c6ec92c0dcc51fed")
                .addFormDataPart("width", "80")
                .addFormDataPart("height", "80")
                .addFormDataPart("left", "-40")
                .addFormDataPart("top", "40")
                .addFormDataPart("rate", "1")
                .addFormDataPart("newUpload", file.getName(), fileUploadRequestBody)
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("upload", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("upload", result);
            }
        });

    }

    private void downloadFile(final File file, String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            long contentLength = 0;
            long writtenBytes = 0;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    progressListener.onUpdate(contentLength,writtenBytes);
                }
            };

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    contentLength = response.body().contentLength();
                    InputStream is = response.body().byteStream();
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[2048];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        writtenBytes += len;
                        mHandler.post(runnable);
                    }
                    fos.flush();
                    is.close();
                    fos.close();
                    bis.close();
                }
            }
        });
    }

    ProgressListener progressListener = new ProgressListener() {
        @Override
        public void onUpdate(long contentLength, long writtenBytes) {
            Log.i("progress", "" + writtenBytes);
        }
    };
}
