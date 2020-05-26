package com.example.myblog.retrofit;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/accounts/check-login/")
    public void login(
            @Field("username") String username,
            @Field("password") String password,
            Callback<LoginResponse> callback
    );

    @GET("/blog/dashboard/list/?limit=100")
    public void listArticles(Callback<Articles> callback);

    @FormUrlEncoded
    @POST("/blog/dashboard/update-or-create/article/")
    public void createPost(
            @Field("title") String title,
            @Field("content") String content,
            @Field("html") String html,
            @Field("owner") int owner,
            Callback<CreateArticleResponse> callback
    );

    @DELETE("/blog/dashboard/delete/article/{uuid}")
    public void deletaArticle(
        @Path("uuid") String uuid,
        Callback<DeleteArticleResponse> callback
    );
}
