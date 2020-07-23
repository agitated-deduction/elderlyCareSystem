package com.example.elderlycaresystem.data.remote;

import android.content.Context;
import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
//    private static final String BASE_URL = "http://192.168.1.29:9090/elderlycare/";
    private static final String BASE_URL = "http://192.168.1.221:3000/";



    private static OkHttpClient client = new OkHttpClient();
    private static OkHttpClient.Builder builder = new OkHttpClient.Builder();



    public static Retrofit getClient(Context context)
    {
        Log.d("Retro_Client",BASE_URL);
        builder.addNetworkInterceptor(new AddCookiesInterceptor(context)); // VERY VERY IMPORTANT
        builder.addInterceptor(new ReceivedCookiesInterceptor(context)); // VERY VERY IMPORTANT
        client = builder.build();

        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    client(client).
                    build();
        }
        return retrofit;
    }
}
