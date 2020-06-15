package com.example.elderlycaresystem.util;

import com.example.elderlycaresystem.data.remote.ElderlyService;
import com.example.elderlycaresystem.data.remote.RetrofitClient;

public class ApiUtils {
    public static ElderlyService getElderlyService() {
        return RetrofitClient.getClient().create(ElderlyService.class);
    }
}
