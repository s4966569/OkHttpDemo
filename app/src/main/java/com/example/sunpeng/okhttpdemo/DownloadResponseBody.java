package com.example.sunpeng.okhttpdemo;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by sunpeng on 2016/10/20.
 */

public class DownloadResponseBody extends ResponseBody {
    private final ResponseBody mResponseBody;
    private final UploadRequestBody.ProgressListener mProgressListener;
    private BufferedSource mBufferedSource;
    private File mFile;

    public DownloadResponseBody(ResponseBody mResponseBody, File file,UploadRequestBody.ProgressListener mProgressListener) {
        this.mResponseBody = mResponseBody;
        this.mProgressListener = mProgressListener;
        this.mFile = file;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if(mBufferedSource  ==null)
            mBufferedSource = Okio.buffer(createForwardingSource(mResponseBody.source()));
        return mBufferedSource;
    }

    private Source createForwardingSource(final Source source){
        return new ForwardingSource(source) {
            long totalBytesRead=0;
            long contentLength;
            Source fileSource=null;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                if(contentLength==0)
                    contentLength=mResponseBody.contentLength();
                if(fileSource==null)
                    fileSource=Okio.source(mFile);
                long bytesRead = super.read(sink, byteCount);
                if(bytesRead != -1){
                    sink.write(fileSource,bytesRead);
                }
                totalBytesRead += bytesRead != -1 ?bytesRead:0;
                if(mProgressListener!=null)
                    mProgressListener.onUpdate(contentLength,totalBytesRead);
                return bytesRead;
            }
        };
    }
}
