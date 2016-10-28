package com.example.sunpeng.okhttpdemo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.File;

/**
 * Created by sunpeng on 16-10-28.
 */

public class DownloadRequest extends BaseRequest {
    @JSONField(serialize = false)
    private File fileToSaveData;                //保存下载数据的文件
    @JSONField(serialize = false)
    private ProgressListener progressListener;  //下载进度回调接口

    public File getFileToSaveData() {
        return fileToSaveData;
    }

    public void setFileToSaveData(File fileToSaveData) {
        this.fileToSaveData = fileToSaveData;
    }

    public ProgressListener getProgressListener() {
        return progressListener;
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }
}
