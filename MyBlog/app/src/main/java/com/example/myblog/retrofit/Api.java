package com.example.myblog.retrofit;

import retrofit.RestAdapter;

public class Api {
    public static ApiInterface getClient(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://letanhsang.pythonanywhere.com")
                .build();

        return adapter.create(ApiInterface.class);
    }
}
