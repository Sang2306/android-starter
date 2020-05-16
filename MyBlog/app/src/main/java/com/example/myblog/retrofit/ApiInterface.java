package com.example.myblog.retrofit;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/accounts/check-login/")
    public void login(
            @Field("username") String username,
            @Field("password") String password,
            Callback<LoginResponse> callback
    );
}
