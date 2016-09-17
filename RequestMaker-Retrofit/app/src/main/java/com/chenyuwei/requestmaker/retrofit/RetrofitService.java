package com.chenyuwei.requestmaker.retrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.FieldMap;

/**
 * Created by 晶 on 2016/3/16.
 */
public interface RetrofitService {

    @FormUrlEncoded
    @POST("getUsers")
    Call<BaseEntity<List<Person>>> getUsers(@FieldMap Map<String, String> map);
}
