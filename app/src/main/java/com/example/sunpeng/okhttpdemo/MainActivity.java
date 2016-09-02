package com.example.sunpeng.okhttpdemo;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

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

public class MainActivity extends AppCompatActivity{
    private Button btn;
    private TextView tv;
    public static final MediaType MEDIA_TYPE
            = MediaType.parse("application/text; charset=utf-8");
    private String url="http://mobile.yanxiu.com/v20/api/guopei/examin";
    private Handler mHandler;
    YXRequest yxRequest = new YXRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        tv = (TextView) findViewById(R.id.tv);

        yxRequest.setToken("7818ae7f832d5594f6641a2f09909610");
        yxRequest.setPid("1289");
        yxRequest.setW("3");
        mHandler= new Handler(Looper.getMainLooper());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    HttpEngine.getInstance().doPost(url,yxRequest, new HttpCallBack() {
                        @Override
                        public void onError(Call call, String errorMsg) {
                            Log.i("fail",errorMsg);
                            tv.setText("返回数据失败！");
                        }
                        @Override
                        public void onSuccess(Call call, String strResponse) {
                            tv.setText("返回数据成功");
                            Log.i("success",strResponse);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
