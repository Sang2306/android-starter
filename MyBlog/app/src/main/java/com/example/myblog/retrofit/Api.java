package com.example.myblog.retrofit;

import retrofit.RestAdapter;

public class Api {
    public static ApiInterface getClient(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://letanhsang.pythonanywhere.com")
                .build();

        ApiInterface api = adapter.create(ApiInterface.class);
        return api;
    }
}
