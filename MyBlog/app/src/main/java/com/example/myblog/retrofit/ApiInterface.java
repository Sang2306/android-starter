package com.example.myblog.retrofit;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("/accounts/check-login/")
    void login(
            @Field("username") String username,
            @Field("password") String password,
            Callback<LoginResponse> callback
    );

    @GET("/blog/dashboard/list/?limit=7")
    void listArticles(Callback<Articles> callback);

    @FormUrlEncoded
    @POST("/blog/dashboard/update-or-create/article/")
    void createAnArticle(
            @Field("title") String title,
            @Field("content") String content,
            @Field("html") String html,
            @Field("owner") int owner,
            Callback<CreateArticleResponse> callback
    );

    @FormUrlEncoded
    @POST("/blog/dashboard/update-or-create/article/")
    void updateAnArticle(
            @Field("uuid") String uuid,
            @Field("title") String title,
            @Field("content") String content,
            @Field("html") String html,
            @Field("owner") int owner,
            Callback<UpdateArticleResponse> callback
    );

    @DELETE("/blog/dashboard/delete/article/{uuid}")
    void deleteArticle(
            @Path("uuid") String uuid,
            Callback<DeleteArticleResponse> callback
    );

    @GET("/blog/dashboard/article/{uuid}")
    void getArticle(
            @Path("uuid") String uuid,
            Callback<Article> callback
    );

    @GET("/blog/dashboard/search-post/")
    void searchArticles(
        @Query(value = "text_search") String textSearch,
        Callback<Articles> callback
    );
}
