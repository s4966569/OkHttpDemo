package com.example.sunpeng.okhttpdemo;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Button btn_load, btn_upload, btn_clear, btn_download,btn_cancel,btn_pause;
    private TextView tv, tv_progress;
    private ProgressBar progressBar;
    public static final MediaType MEDIA_TYPE
            = MediaType.parse("application/text; charset=utf-8");
    private String examUrl = "http://mobile.yanxiu.com/v20/api/guopei/examine";
    private String submitQUrl = "http://test.hwk.yanxiu.com/app/q/submitQ.do";
    private String quesListUrl = "http://test.hwk.yanxiu.com/app/personalData/getQuestionList.do";
    private String uploadUrl = "http://mobile.yanxiu.com/v20/api/resource/uploadheader";
//    private String ucUrl = "http://106.2.184.227:9999/wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V11.1.0.870_android_pf145_(Build161012223751).apk?auth_key=1477648349-0-0-f7030bd113a4683c72480fafce14c9ba&vh=31b32982c17a91fb081019e19bbd54db&SESSID=478287a2d8f3c123957300e60dd127c6";
    private String ucUrl = "http://106.2.184.230:9999/wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V11.1.5.871_android_pf145_(Build161028102549).apk?auth_key=1479264762-0-0-2d7f1487317c0f752abbbd76a072b0ab&vh=220b7fe376c3b7b8eea9ee5a182a25b6&SESSID=3e1bd80905d87b4c07ffaf8f8faed2dd";
    private String osType = "0";
    private String pcode = "010110000";
    private String token = "e95dab459d35c9be935f39403ba8d8f2";
    private String version = "2.3.2.1";
    private ExamRequest examRequest = new ExamRequest();
    private QuestionListRequest questionListRequest = new QuestionListRequest();
    private SubmitQuestionRequest submitQuestionRequest = new SubmitQuestionRequest();
    private PortraitUploadRequest portraitUploadRequest;
    private DownloadRequest downloadRequest;
    private File uploadFile, downloadFile;
    private boolean hasSetProgressMax = false;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        btn_load = (Button) findViewById(R.id.btn_load);
        tv = (TextView) findViewById(R.id.tv);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_download = (Button) findViewById(R.id.btn_download);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        initData();
        initListener();

    }

    private void initData() {
        mHandler = new Handler(Looper.getMainLooper());
        uploadFile = new File(Environment.getExternalStorageDirectory() + "/yanxiu/12131.jpg");
        downloadFile = new File(Environment.getExternalStorageDirectory() + "/yanxiu/uc.apk");
        if (!downloadFile.exists()) {
            try {
                downloadFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FetchUserRequest request = new FetchUserRequest();
        examRequest.setUrl(UrlReposity.getAbsoluteUrl(UrlReposity.ACTION_EXAM));
//        examRequest.setRequestType(RequestType.POST);
        examRequest.setTag("exam");
        examRequest.setToken("7818ae7f832d5594f6641a2f09909610");
        examRequest.setPid("1289");
        examRequest.setW("3");

        submitQuestionRequest.setUrl(submitQUrl);
        submitQuestionRequest.setTag("submitQuestion");
        submitQuestionRequest.setTag(UrlReposity.ACTION_EXAM);
        submitQuestionRequest.setOsType(osType);
        submitQuestionRequest.setPcode(pcode);
        submitQuestionRequest.setToken(token);

        questionListRequest.setUrl(quesListUrl);
        questionListRequest.setRequestType(RequestType.POST);
        questionListRequest.setTag("getQuestionList");
        questionListRequest.setDecrypt(true);
        questionListRequest.setPaperId("20999");
        questionListRequest.setOsType(osType);
        questionListRequest.setPcode(pcode);
        questionListRequest.setToken(token);
        questionListRequest.setVersion(version);

        portraitUploadRequest = new PortraitUploadRequest();
        portraitUploadRequest.setRequestType(RequestType.POST);
        portraitUploadRequest.setTag("uploadPortrait");
        portraitUploadRequest.setFileToUpload(uploadFile);
        portraitUploadRequest.setUrl(UrlReposity.getAbsoluteUrl(UrlReposity.ACTION_UPLOAD_HEADER));
        portraitUploadRequest.setFileKey("newUpload");
        portraitUploadRequest.setToken("5372dd841e2ca489c6ec92c0dcc51fed");
        portraitUploadRequest.setWidth("80");
        portraitUploadRequest.setHeight("80");
        portraitUploadRequest.setLeft("-40");
        portraitUploadRequest.setTop("40");
        portraitUploadRequest.setRate("1");
        portraitUploadRequest.setProgressListener(progressListener);

        downloadRequest = new DownloadRequest();
        downloadRequest.setUrl(ucUrl);
        downloadRequest.setTag("downloadUc");
        downloadRequest.setRequestType(RequestType.DOWNLOAD);
        downloadRequest.setFileToSaveData(downloadFile);
        downloadRequest.setProgressListener(progressListener);
    }

    private void initListener(){

        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("");
                HttpManager.getInstance().invoke(examRequest, ExamResponse.class, new HttpCallBack<ExamResponse>() {
                    @Override
                    public void onError(Call call, String errorMsg) {
                        Log.i("error", errorMsg);
                        tv.setText(errorMsg);

                        examRequest.getToken();
                    }

                    @Override
                    public void onSuccess(Call call, ExamResponse response) {
                        ExamResponse exam = response;
                        tv.setText(exam.getDesc());
                    }
                });

                HttpManager.getInstance().invoke(questionListRequest, JSONObject.class, new HttpCallBack<JSONObject>() {
                    @Override
                    public void onError(Call call, String errorMsg) {

                    }

                    @Override
                    public void onSuccess(Call call, JSONObject o) {

                    }
                });
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasSetProgressMax=false;
                HttpManager.getInstance().invoke(portraitUploadRequest, JSONObject.class, new HttpCallBack<JSONObject>() {
                    @Override
                    public void onError(Call call, String errorMsg) {
                        Log.i("error", errorMsg);
                        Toast.makeText(mContext,"上传失败！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(Call call, JSONObject o) {
                        Toast.makeText(mContext,"上传成功！",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasSetProgressMax=false;
                HttpEngine.getInstance().invoke(downloadRequest, null, new HttpCallBack<File>() {
                    @Override
                    public void onError(Call call, String errorMsg) {
                        Log.i("error", errorMsg);
                        Toast.makeText(mContext,errorMsg,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(Call call, File file) {
                        Toast.makeText(mContext,"下载成功！",Toast.LENGTH_SHORT).show();
                        Toast.makeText(mContext,file.getName(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setMax(100);
                progressBar.setProgress(0);
                tv_progress.setText("当前进度：0");
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpEngine.getInstance().cancel("downloadUc",true);
                HttpEngine.getInstance().cancel("uploadPortrait",true);
            }
        });

    }

    ProgressListener progressListener = new ProgressListener() {
        @Override
        public void onUpdate(long contentLength, long writtenBytes) {
            if (!hasSetProgressMax) {
                progressBar.setMax((int) contentLength);
                hasSetProgressMax = true;
                Log.i("max", "" + contentLength);
            }
            progressBar.setProgress((int) writtenBytes);
            tv_progress.setText("当前进度："+writtenBytes+"/"+contentLength);
            Log.i("progress", "" + writtenBytes);
        }
    };
}
