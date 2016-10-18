package com.example.sunpeng.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import okhttp3.Call;
import okhttp3.MediaType;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView tv;
    public static final MediaType MEDIA_TYPE
            = MediaType.parse("application/text; charset=utf-8");
    private String examUrl = "http://mobile.yanxiu.com/v20/api/guopei/examine";
    private String submitQUrl = "http://dev.hwk.yanxiu.com/app/q/submitQ.do";
    private String quesListUrl = "http://dev.hwk.yanxiu.com/app/personalData/getQuestionList.do";
    private String osType="0";
    private String pcode="010110000";
    private String token="834fe6b8909824f16b5d75c141f5a340";
    private String version="2.2.2";
    private ExamRequest examRequest = new ExamRequest();
    private QuestionListRequest questionListRequest = new QuestionListRequest();
    private SubmitQuestionRequest submitQuestionRequest = new SubmitQuestionRequest();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        tv = (TextView) findViewById(R.id.tv);

        initData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("");
                HttpEngine.getInstance().invoke(examRequest, ExamResponse.class, new HttpCallBack<ExamResponse>() {
                    @Override
                    public void onError(Call call, String errorMsg) {
                        Log.i("error", errorMsg);
                        tv.setText(errorMsg);
                    }
                    @Override
                    public void onSuccess(Call call, ExamResponse response) {
                        ExamResponse exam = response;
                        tv.setText(exam.getDesc());
                    }
                });
            }
        });

    }

    private void initData() {
        examRequest.setUrl(examUrl);
        examRequest.setRequestType(RequestType.POST);
        examRequest.setToken("7818ae7f832d5594f6641a2f09909610");
        examRequest.setPid("1289");
        examRequest.setW("3");

        submitQuestionRequest.setUrl(submitQUrl);
        submitQuestionRequest.setOsType(osType);
        submitQuestionRequest.setPcode(pcode);
        submitQuestionRequest.setToken(token);

        questionListRequest.setUrl(quesListUrl);
        questionListRequest.setPaperId("19515");
        questionListRequest.setOsType(osType);
        questionListRequest.setPcode(pcode);
        questionListRequest.setToken(token);
        questionListRequest.setVersion(version);
    }
}
