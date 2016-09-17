package com.chenyuwei.requestmaker.retrofit;

import android.app.Application;

/**
 * Created by vivi on 2016/9/17.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RequestMaker.setBaseUrl("http://192.168.1.113:8080/MyWeb/");
    }
}
