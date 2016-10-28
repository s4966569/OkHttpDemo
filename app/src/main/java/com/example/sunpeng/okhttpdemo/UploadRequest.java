package com.example.sunpeng.okhttpdemo;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.File;
import java.io.Serializable;

/**
 * Created by sunpeng on 16-10-28.
 */

public class UploadRequest extends BaseRequest{
    @JSONField(serialize = false)
    private File fileToUpload;              //待上传的文件
    @JSONField(serialize = false)
    private String fileKey;                 //与待上传的File对应的form表单的key
    @JSONField(serialize = false)
    private ProgressListener progressListener; // 上传进度接口回调

    public File getFileToUpload() {
        return fileToUpload;
    }

    public void setFileToUpload(File fileToUpload) {
        this.fileToUpload = fileToUpload;
    }

    public ProgressListener getProgressListener() {
        return progressListener;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }
}
