package com.sks.amago.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CallsInterface {

    @FormUrlEncoded
    @POST("api/users/register")
    Call<ResponseBody> Register(
            @Field("username") String fullname,
            @Field("password") String pinnumber,
            @Field("phone") String phonenumber
    );

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<ResponseBody> Login(
            @Field("phone") String phonenumber,
            @Field("password") String pinnumber
    );

    @POST("api/auth/current")
    Call<ResponseBody> CheckToken(
            @Header("Authorization") String utoken
    );

//    @GET("api/getHarvest/id")
//    Call<> GetHarvest()
}
