package com.example.sunpeng.okhttpdemo;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements HttpCallBack {
    private Button btn;
    private TextView tv;
    public static final MediaType MEDIA_TYPE
            = MediaType.parse("application/text; charset=utf-8");
    private String content ="";
    private String url="http://mobile.yanxiu.com/test/api/guopei/examine";
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        tv = (TextView) findViewById(R.id.tv);
        content ="{\"token\":\"86dc537de1e481ee15c99a0aa2bd3e22\",\"pid\":\"1789\",\"w\":\"3\"}";
        content="token="+"86dc537de1e481ee15c99a0aa2bd3e22"+"&"+"pid="+"1789"+"&"+"w="+"3";
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    post(url, new HttpCallBack() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i("fail",e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.isSuccessful()){
                                tv.setText("返回数据成功");
                                try {
                                    Log.i("success",response.body().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                tv.setText("返回数据失败！");
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mHandler= new Handler(Looper.getMainLooper());

    }




    private void post(String url,final HttpCallBack callBack) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(MEDIA_TYPE,json);
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token","86dc537de1e481ee15c99a0aa2bd3e22");
        formBody.add("pid","1789");
        formBody.add("w","3");
        RequestBody requestBody = formBody.build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(callBack!=null)
                            callBack.onFailure(call,e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(callBack!=null)
                            callBack.onResponse(call,response);
                    }
                });
            }
        });
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) {

    }
}
