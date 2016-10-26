package com.example.sunpeng.okhttpdemo;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by sunpeng on 2016/10/20.
 */

public class UploadRequestBody extends RequestBody {
    private final ProgressListener mProgressListener;
    private final RequestBody mRequestBody;
    private BufferedSink mBufferedSink;
    private Handler mHandler;

    public UploadRequestBody(@NonNull RequestBody requestBody, ProgressListener progressListener){
        mRequestBody=requestBody;
        mProgressListener=progressListener;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        if(mBufferedSink==null)
            mBufferedSink = Okio.buffer(createForwardingSink(bufferedSink));
        mRequestBody.writeTo(mBufferedSink);
        mBufferedSink.flush();
    }



    public ForwardingSink createForwardingSink(Sink sink){
        return new ForwardingSink(sink) {
            long contentLength,writtenBytes;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                  mProgressListener.onUpdate(contentLength,writtenBytes);
                }
            };
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if(contentLength==0)
                    contentLength=mRequestBody.contentLength();
                writtenBytes+=byteCount;
                if(mProgressListener!=null)
                    mHandler.post(runnable);
            }
        };
    }
}
