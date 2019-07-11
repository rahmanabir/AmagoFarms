package com.sks.amago.Retrofit;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class RetrofitClient {

    private Retrofit retrofit;
    private static RetrofitClient retrofitClient;
    private static final String BASE_URL = "http://192.168.0.109:5000/";

    private RetrofitClient(){
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getRetrofitInstance(){
        if(retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }
        Log.i(TAG, "getRetrofitInstance: Static Instance Has Been Initialized!");
        return retrofitClient;
    }

    public CallsInterface getAPICalls(){
        return retrofit.create(CallsInterface.class);
    }
}