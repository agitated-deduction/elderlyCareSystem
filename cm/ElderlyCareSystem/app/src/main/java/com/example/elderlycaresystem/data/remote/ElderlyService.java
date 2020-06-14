package com.example.elderlycaresystem.data.remote;

import com.example.elderlycaresystem.data.ElderlyListResponse;
import com.example.elderlycaresystem.data.InfoResponse;
import com.example.elderlycaresystem.data.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ElderlyService {
    @FormUrlEncoded
    @POST("/users/login")
    Call<LoginResponse> login(
            @Field("id") String id,
            @Field("pass_word") String passWord
    );

    @GET("/users/list/{id}")
    Call<ElderlyListResponse> getMainList();

    @GET("/users/data/{id}")
    Call<InfoResponse> getinfoList();

}
