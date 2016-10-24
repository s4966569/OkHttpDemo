package com.example.sunpeng.okhttpdemo;

/**
 * Created by sunpeng on 2016/10/24.
 */

public class MockEngine {
    private static MockEngine mMockEngine = null;

    public static MockEngine getInstance() {
        if (mMockEngine == null) {
            synchronized (MockEngine.class) {
                if (mMockEngine == null)
                    mMockEngine = new MockEngine();
            }
        }
        return mMockEngine;
    }

    public <T> void invoke(BaseRequest baseRequest, Class<T> clazz, HttpCallBack<T> httpCallBack) {
        T t;
        t = FileUtils.getStringFromInputStream(FileUtils.getInputStreamFromAsset(baseRequest.getMockFileName()), clazz);
        if (httpCallBack != null) {
            if (t != null) {
                httpCallBack.onSuccess(null, t);
            } else {
                httpCallBack.onError(null, "");
            }
        }
    }
}
