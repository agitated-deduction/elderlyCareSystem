package com.example.elderlycaresystem.util;

import android.content.Context;

import com.example.elderlycaresystem.data.remote.ElderlyService;
import com.example.elderlycaresystem.data.remote.RetrofitClient;

public class ApiUtils {
    public static ElderlyService getElderlyService(Context context) {
        return RetrofitClient.getClient(context).create(ElderlyService.class);
    }
}
