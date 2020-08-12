package com.example.elderlyversion.data.remote;

import com.example.elderlyversion.data.ElderlyData;
import com.example.elderlyversion.data.KeyData;
import com.example.elderlyversion.data.LoginData;
import com.example.elderlyversion.data.RootModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ElderlyService {

//    @PUT("/users/{key}/datas")
//    Call<ResponseBody> putElderlyData(@Path("key") String id, @Body ElderlyData eldelyData);

//    @POST("http://192.168.1.221:3001/memo")
//    Call<ResponseBody> putElderlyData(@Body ElderlyData eldelyData);

    // 서버에 로그인
//    @POST("/admin")
//    Call<KeyData> login(@Body LoginData loginData);


    // TODO : GET -> POST로 변경 && @Query -> @Body LoginData loginData
    /**
     Login to Real Server
     **/
    @POST("devices/login")
    Call<KeyData> login(@Body LoginData loginData);

    @POST("devices/login")
    Call<ResponseBody> login2(@Body LoginData loginData);

    @GET("devices/{num}/data")
    Call<ResponseBody> getElderlyData(@Path("num") int key);

    @GET("datas")
    Call<ResponseBody> putElderlyData2(@Query("data") String data);

    @POST("datas")
    Call<ResponseBody> putElderlyData(@Body ElderlyData eldelyData);

    @POST("https://fcm.googleapis.com/fcm/send")
    Call<ResponseBody> emergencyState(@Body RootModel rootModel);
}
