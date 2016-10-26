package com.example.sunpeng.okhttpdemo;

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

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button btn, btn_upload, btn_clear, btn_download;
    private TextView tv, tv_progress;
    private ProgressBar progressBar;
    public static final MediaType MEDIA_TYPE
            = MediaType.parse("application/text; charset=utf-8");
    private String examUrl = "http://mobile.yanxiu.com/v20/api/guopei/examine";
    private String submitQUrl = "http://dev.hwk.yanxiu.com/app/q/submitQ.do";
    private String quesListUrl = "http://dev.hwk.yanxiu.com/app/personalData/getQuestionList.do";
    private String uploadUrl = "http://mobile.yanxiu.com/v20/api/resource/uploadheader";
    private String ucUrl = "http://106.2.184.227:9999/wap3.ucweb.com/files/UCBrowser/zh-cn/999/UCBrowser_V11.1.0.870_android_pf145_(Build161012223751).apk?auth_key=1477648349-0-0-f7030bd113a4683c72480fafce14c9ba&vh=31b32982c17a91fb081019e19bbd54db&SESSID=478287a2d8f3c123957300e60dd127c6";
    private String osType = "0";
    private String pcode = "010110000";
    private String token = "834fe6b8909824f16b5d75c141f5a340";
    private String version = "2.2.2";
    private ExamRequest examRequest = new ExamRequest();
    private QuestionListRequest questionListRequest = new QuestionListRequest();
    private SubmitQuestionRequest submitQuestionRequest = new SubmitQuestionRequest();
    private File uploadFile, downloadFile;
    private boolean hasSetProgressMax = false;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        tv = (TextView) findViewById(R.id.tv);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_download = (Button) findViewById(R.id.btn_download);
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

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasSetProgressMax=false;
                uploadPortrait(uploadFile);
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasSetProgressMax=false;
                downloadFile(downloadFile, ucUrl);
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setMax(100);
                progressBar.setProgress(0);
                tv_progress.setText("当前上传：0");
            }
        });

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

    private void uploadPortrait(File file) {
        OkHttpClient httpClient = new OkHttpClient();
        UploadRequestBody uploadRequestBody = new UploadRequestBody(RequestBody.create(MediaType.parse("application/octet-stream"), file), progressListener);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody formBody = builder.setType(MultipartBody.FORM)
                .addFormDataPart("token", "5372dd841e2ca489c6ec92c0dcc51fed")
                .addFormDataPart("width", "80")
                .addFormDataPart("height", "80")
                .addFormDataPart("left", "-40")
                .addFormDataPart("top", "40")
                .addFormDataPart("rate", "1")
                .addFormDataPart("newUpload", file.getName(), uploadRequestBody)
                .build();
        Request request = new Request.Builder().url(uploadUrl).post(formBody).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("upload", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("upload", result);
                hasSetProgressMax = false;
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
                    int len = 0;
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
            if (!hasSetProgressMax) {
                progressBar.setMax((int) contentLength);
                hasSetProgressMax = true;
                Log.i("max", "" + contentLength);
            }
            progressBar.setProgress((int) writtenBytes);
            tv_progress.setText("当前上传："+writtenBytes+"/"+contentLength);
            Log.i("upload", "" + writtenBytes);
        }
    };
}
