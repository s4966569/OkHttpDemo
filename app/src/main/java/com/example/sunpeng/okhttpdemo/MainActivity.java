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

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView tv;
    public static final MediaType MEDIA_TYPE
            = MediaType.parse("application/text; charset=utf-8");
    private String url = "http://mobile.yanxiu.com/v20/api/guopei/examine";
    private String submitQUrl = "http://dev.hwk.yanxiu.com/app/q/submitQ.do";
    private String quesListUrl = "http://dev.hwk.yanxiu.com/app/personalData/getQuestionList.do";
    private String osType="0";
    private String pcode="010110000";
    private String token="834fe6b8909824f16b5d75c141f5a340";
    private String version="2.2.2";
    private String answers="{\"chapterId\":\"64109\",\"ptype\":1,\"paperDetails\":[{\"answer\":[{\"answer\":[],\"qid\":\"2740232\"}],\"children\":[{\"id\":\"92468\",\"qid\":\"2740232\",\"costtime\":5,\"ptid\":146827,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146826,\"qid\":\"2740231\",\"id\":\"92469\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2740264\"}],\"children\":[{\"id\":\"92470\",\"qid\":\"2740264\",\"costtime\":0,\"ptid\":146829,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146828,\"qid\":\"2740242\",\"id\":\"92471\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146830,\"qid\":\"2740229\",\"id\":\"92472\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146831,\"qid\":\"2745407\",\"id\":\"92473\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146832,\"qid\":\"2745408\",\"id\":\"92474\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146833,\"qid\":\"2745445\",\"id\":\"92475\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146834,\"qid\":\"2740251\",\"id\":\"92476\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146835,\"qid\":\"2745411\",\"id\":\"92477\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146836,\"qid\":\"2745412\",\"id\":\"92478\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146837,\"qid\":\"2740244\",\"id\":\"92478\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146838,\"qid\":\"2745413\",\"id\":\"92478\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146839,\"qid\":\"2745414\",\"id\":\"92478\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146840,\"qid\":\"2740245\",\"id\":\"92478\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146841,\"qid\":\"2740263\",\"id\":\"92479\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2740287\"},{\"answer\":[],\"qid\":\"2740288\"},{\"answer\":[],\"qid\":\"2740289\"},{\"answer\":[],\"qid\":\"2740290\"}],\"children\":[{\"id\":\"92480\",\"qid\":\"2740287\",\"costtime\":0,\"ptid\":146843,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92481\",\"qid\":\"2740288\",\"costtime\":0,\"ptid\":146844,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92482\",\"qid\":\"2740289\",\"costtime\":0,\"ptid\":146845,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92483\",\"qid\":\"2740290\",\"costtime\":0,\"ptid\":146846,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146842,\"qid\":\"2740246\",\"id\":\"92484\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2745388\"},{\"answer\":[],\"qid\":\"2745389\"},{\"answer\":[],\"qid\":\"2745390\"},{\"answer\":[],\"qid\":\"2745391\"},{\"answer\":[],\"qid\":\"2745392\"},{\"answer\":[],\"qid\":\"2745393\"},{\"answer\":[],\"qid\":\"2745394\"},{\"answer\":[],\"qid\":\"2745395\"}],\"children\":[{\"id\":\"92485\",\"qid\":\"2745388\",\"costtime\":0,\"ptid\":146848,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92486\",\"qid\":\"2745389\",\"costtime\":0,\"ptid\":146849,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92487\",\"qid\":\"2745390\",\"costtime\":0,\"ptid\":146850,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92488\",\"qid\":\"2745391\",\"costtime\":0,\"ptid\":146851,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92489\",\"qid\":\"2745392\",\"costtime\":0,\"ptid\":146852,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92490\",\"qid\":\"2745393\",\"costtime\":0,\"ptid\":146853,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92491\",\"qid\":\"2745394\",\"costtime\":0,\"ptid\":146854,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92492\",\"qid\":\"2745395\",\"costtime\":0,\"ptid\":146855,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146847,\"qid\":\"2740252\",\"id\":\"92493\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146856,\"qid\":\"2745409\",\"id\":\"92494\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146857,\"qid\":\"2745410\",\"id\":\"92495\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146858,\"qid\":\"2745416\",\"id\":\"92496\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146859,\"qid\":\"2745418\",\"id\":\"92497\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2745421\"},{\"answer\":[],\"qid\":\"2745422\"}],\"children\":[{\"id\":\"92498\",\"qid\":\"2745421\",\"costtime\":0,\"ptid\":146861,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92499\",\"qid\":\"2745422\",\"costtime\":0,\"ptid\":146862,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146860,\"qid\":\"2745420\",\"id\":\"92500\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2745424\"},{\"answer\":[],\"qid\":\"2745425\"},{\"answer\":[],\"qid\":\"2745426\"}],\"children\":[{\"id\":\"92501\",\"qid\":\"2745424\",\"costtime\":0,\"ptid\":146864,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92502\",\"qid\":\"2745425\",\"costtime\":0,\"ptid\":146865,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92503\",\"qid\":\"2745426\",\"costtime\":0,\"ptid\":146866,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146863,\"qid\":\"2745423\",\"id\":\"92504\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146867,\"qid\":\"2740262\",\"id\":\"92505\",\"status\":3,\"uid\":168},{\"answer\":[],\"costtime\":0,\"ptid\":146868,\"qid\":\"2740261\",\"id\":\"92506\",\"status\":3,\"uid\":168},{\"answer\":[{\"answer\":[],\"qid\":\"2745404\"},{\"answer\":[],\"qid\":\"2745405\"},{\"answer\":[],\"qid\":\"2745406\"}],\"children\":[{\"id\":\"92507\",\"qid\":\"2745404\",\"costtime\":0,\"ptid\":146870,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92508\",\"qid\":\"2745405\",\"costtime\":0,\"ptid\":146871,\"status\":3,\"uid\":168,\"answer\":[]},{\"id\":\"92509\",\"qid\":\"2745406\",\"costtime\":0,\"ptid\":146872,\"status\":3,\"uid\":168,\"answer\":[]}],\"costtime\":0,\"ptid\":146869,\"qid\":\"2745400\",\"id\":\"92510\",\"status\":3,\"uid\":168}],\"paperStatus\":{\"begintime\":1472998490014,\"endtime\":1472998495180,\"costtime\":5,\"ppid\":19390,\"id\":\"16405\",\"status\":\"2\",\"tid\":0,\"uid\":168}}";
    private YXRequest yxRequest = new YXRequest();
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
                HttpEngine.getInstance().doPost(url, yxRequest, ExamResponse.class, new HttpCallBack<ExamResponse>() {
                    @Override
                    public void onError(Call call, String errorMsg) {
                        Log.i("fail", errorMsg);
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
        yxRequest.setToken("7818ae7f832d5594f6641a2f09909610");
        yxRequest.setPid("1289");
        yxRequest.setW("3");

        submitQuestionRequest.setOsType(osType);
        submitQuestionRequest.setPcode(pcode);
        submitQuestionRequest.setToken(token);
        submitQuestionRequest.setAnswers(answers);

        questionListRequest.setPaperId("19515");
        questionListRequest.setOsType(osType);
        questionListRequest.setPcode(pcode);
        questionListRequest.setToken(token);
        questionListRequest.setVersion(version);
    }
}
