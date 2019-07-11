package com.sks.amago.Retrofit;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class RFClientInstance {

    private Retrofit retrofit;
    private static RetrofitClient retrofitInstance;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private Retrofit(){
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getRetrofitInstance(){
        if(retrofitInstance== null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        Log.i(TAG, "getRetrofitInstance: Static Instance Has Been Initialized!");
        return retrofit;
    }

}