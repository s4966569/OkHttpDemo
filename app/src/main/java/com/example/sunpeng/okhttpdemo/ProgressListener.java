package com.example.sunpeng.okhttpdemo;

import android.os.Handler;

/**
 * Created by sunpeng on 2016/10/25.
 */

public interface ProgressListener {

    void onUpdate(long contentLength,long writtenBytes);
}
