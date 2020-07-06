package com.example.elderlycaresystem.data.remote;

import com.example.elderlycaresystem.data.login.LoginJson;
import com.example.elderlycaresystem.data.elderly.ElderlyListResponse;
import com.example.elderlycaresystem.info.InfoResponse;
import com.example.elderlycaresystem.data.login.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ElderlyService {
    @POST("users/login")
    Call<LoginResponse> login(@Body LoginJson loginJson);

    @GET("/users/list/{id}")
    Call<ElderlyListResponse> getMainList(
            @Field("id") String id
    );

    @GET("/users/data/{id}")
    Call<InfoResponse> getinfoList(
            @Field("id") String id
    );

    //@FormUrlEncoded
    @GET("devices")
    Call<ResponseBody> getDeviceList(@Query("uid") String id);

}
