package com.pomelo.searchcustomer.network;


import com.pomelo.searchcustomer.utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.ContentValues.TAG;

/**
 * Created by wanghaoxiang on 2019-07-17.
 * 日志拦截器
 */

public class LoggingInterceptor implements Interceptor {
    private final int byteCount = 1024 * 1024;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtils.e(TAG, "----------Request Start----------------");
        LogUtils.e(TAG, "| " + request.toString() + request.headers().toString());
        LogUtils.e(TAG, "| Response:" + content);
        LogUtils.e(TAG, "----------Request End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }
}
