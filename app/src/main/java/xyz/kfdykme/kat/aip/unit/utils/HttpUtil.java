/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package xyz.kfdykme.kat.aip.unit.utils;

import java.io.IOException;


import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xyz.kfdykme.kat.aip.unit.APIService;
import xyz.kfdykme.kat.aip.unit.exception.UnitError;
import xyz.kfdykme.kat.aip.unit.listener.OnResultListener;
import xyz.kfdykme.kat.aip.unit.model.AccessToken;
import xyz.kfdykme.kat.aip.unit.parser.AccessTokenParser;
import xyz.kfdykme.kat.aip.unit.parser.Parser;
import xyz.kfdykme.kat.model.UNITResult;
import xyz.kfdykme.kat.util.FileHelper;

/**
 * 使用okhttp请求tokeh和调用服务
 */
public class HttpUtil {

    private OkHttpClient client;
    private Handler handler;
    private static volatile HttpUtil instance;

    private HttpUtil() {
    }

    public static HttpUtil getInstance() {
        if (instance == null) {
            synchronized (HttpUtil.class) {
                if (instance == null) {
                    instance = new HttpUtil();
                }
            }
        }
        return instance;
    }

    public void init() {
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    public <T> void post(String path, String params, final Parser<T> parser, final OnResultListener<T>
            listener) {


        RequestBody body = RequestBody.create(MediaType.parse("application/json"), params);
        final Request request = new Request.Builder()
                .url(path)
                .post(body)
                .build();

        if (client == null) {
            UnitError err = new UnitError(-999, "okhttp inner error");
            listener.onError(err);
            return;
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                final UnitError error = new UnitError(UnitError.ErrorCode.NETWORK_REQUEST_ERROR,
                        "network request error", e);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onError(error);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                Log.i("HttpUtil",responseString);
                FileHelper.updateFile("http",responseString,System.currentTimeMillis()+"","kh");
                final  UNITResult unitResult;

                unitResult = new Gson().fromJson(responseString, UNITResult.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onUnitResult(unitResult);
                    }
                });


                final T result;
                try {
                    result = parser.parse(responseString);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onResult(result);
                        }
                    });
                } catch (UnitError ocrError) {
                    ocrError.printStackTrace();
                }

            }
        });
    }

    public void getAccessToken(final OnResultListener<AccessToken> listener, String url, String param) {

        final AccessTokenParser accessTokenParser = new AccessTokenParser();
        RequestBody body = RequestBody.create(MediaType.parse("text/html"), param);
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                UnitError error = new UnitError(UnitError.ErrorCode.NETWORK_REQUEST_ERROR, "network request error", e);
                listener.onError(error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response == null || response.body() == null || TextUtils.isEmpty(response.toString())) {
                    throwError(listener, UnitError.ErrorCode.ACCESS_TOKEN_PARSE_ERROR,
                            "token is parse error, please rerequest token");
                }
                try {
                    AccessToken accessToken = accessTokenParser.parse(response.body().string());
                    if (accessToken != null) {
                        APIService.getInstance().setAccessToken(accessToken.getAccessToken());
                        listener.onResult(accessToken);
                    } else {
                        throwError(listener, UnitError.ErrorCode.ACCESS_TOKEN_PARSE_ERROR,
                                "token is parse error, please rerequest token");
                    }
                } catch (UnitError error) {
                    error.printStackTrace();
                    listener.onError(error);
                }
            }
        });

    }

    /**
     * throw error
     *
     * @param errorCode
     * @param msg
     * @param listener
     */
    private static void throwError(OnResultListener<AccessToken> listener, int errorCode, String msg) {
        UnitError error = new UnitError(errorCode, msg);
        listener.onError(error);
    }

    /**
     * 释放资源
     */
    public void release() {
        client = null;
        handler = null;
    }
}
