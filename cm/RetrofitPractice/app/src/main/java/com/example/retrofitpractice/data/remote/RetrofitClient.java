package com.example.retrofitpractice.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    //https://jsonplaceholder.typicode.com/
    //http://192.168.1.29:9090/elderlycare/
    // json to gson => http://www.jsonschema2pojo.org/

    // 2. getApiService() : Retrofit 객체에 받아서 Service 설정하고 반환
    public static ApiService getApiService(){
        return getClient().create(ApiService.class);
    }

    // 1. getClient : Retrofit 객체(Convert to Gson 형태로) 반환
    private static Retrofit getClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
