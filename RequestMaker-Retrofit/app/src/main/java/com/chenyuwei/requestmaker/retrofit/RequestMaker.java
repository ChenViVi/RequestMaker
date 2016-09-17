package com.chenyuwei.requestmaker.retrofit;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 晶 on 2016/3/18.
 */
public abstract class RequestMaker<T> {

    private static String baseUrl = "";
    public static RetrofitService service = null;

    private Activity activity;
    private Call<BaseEntity<T>> call;
    private Map<String,String> map = new HashMap<>();

    public RequestMaker(Activity activity) {
        this.activity = activity;
        if (service == null){
            service =  new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RetrofitService.class);
        }
        call = onRequest(service,map);
        call.enqueue(new Callback<BaseEntity<T>>() {
            @Override
            public void onResponse(Call<BaseEntity<T>> call, Response<BaseEntity<T>> response) {
                onSuccess(response.body().getData());
                response.body().toString();
            }

            @Override
            public void onFailure(Call<BaseEntity<T>> call, Throwable t) {
                onFail();
            }
        });
    }

    public RequestMaker(Activity activity, final String tag) {
        this.activity = activity;
        if (service == null){
            service =  new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RetrofitService.class);
        }
        call = onRequest(service,map);
        call.enqueue(new Callback<BaseEntity<T>>() {
            @Override
            public void onResponse(Call<BaseEntity<T>> call, Response<BaseEntity<T>> response) {
                onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<BaseEntity<T>> call, Throwable t) {
                onFail();
            }
        });
        if (tag != null){
            Log.e("response",tag + " => "+ "<URL>: " + call.request().url().toString());
            Log.e("response",tag + " => "+ "<METHOD>: " + call.request().method());
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String)entry.getKey();
                String val = (String)entry.getValue();
                Log.e("response",tag + " => " + "<MAP>: "+ key + "=" + val);
            }
        }
    }

    protected abstract void onSuccess(T response);

    protected abstract Call<BaseEntity<T>> onRequest(RetrofitService service,Map<String, String> map);

    public static void setBaseUrl(String baseUrl) {
        RequestMaker.baseUrl = baseUrl;
    }

    protected void onFail(){
        Toast.makeText(activity,"网络连接失败", Toast.LENGTH_SHORT).show();
    }
}
