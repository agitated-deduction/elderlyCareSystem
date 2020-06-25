package com.example.retrofitpractice.util;

import com.example.retrofitpractice.data.remote.ApiService;
import com.example.retrofitpractice.data.remote.RetrofitClient;

public class ApiUtils {
    public static ApiService getApiService() {
        return RetrofitClient.getApiService();
    }
}
