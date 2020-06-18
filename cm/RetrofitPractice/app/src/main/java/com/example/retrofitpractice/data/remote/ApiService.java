package com.example.retrofitpractice.data.remote;

import com.example.retrofitpractice.data.TestData;
import com.example.retrofitpractice.data.TestResponse;
import com.example.retrofitpractice.data.UserResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // TODO : 로그인 URI에 아이디 비번 넘겨주기
    @FormUrlEncoded
    @POST("users/login")
    Call<ResponseBody> Login(
            @Field("uid") String id,
            @Field("upwd") String pw
    );

    // 로그인 후에 데이터 받기
    @GET("devices")
    Call<ResponseBody> getDevices();

    // ★ 1번. 김영희씨 데이터(JSON Data) 가져와보기
    @GET("users/staff101058")
    Call<UserResponse> getStaff();

    // ★ 2번. List<Data> 형식으로 된 JSON 받기
    @GET("comments")
    Call<List<TestData>> getComments(@Query("postId")int postId);


}