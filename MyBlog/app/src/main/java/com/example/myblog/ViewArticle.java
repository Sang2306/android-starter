package com.example.myblog;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ViewArticle extends AppCompatActivity {

    WebView articleWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);
        setControl();

        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");

        ViewArticleAsyncTask viewArticleAsyncTask = new ViewArticleAsyncTask();
        viewArticleAsyncTask.execute(uuid);
    }

    private void setControl(){
        articleWebView = findViewById(R.id.articleWebView);
    }

    @SuppressLint("StaticFieldLeak")
    public class ViewArticleAsyncTask extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String uuid = strings[0];
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://letanhsang.pythonanywhere.com/blog/dashboard/get-content/?format=json&uuid=" + uuid)
                    .method("GET", null)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String slug = jsonObject.getString("slug");
                articleWebView.setWebViewClient(new WebViewClient());
                WebSettings webSettings =  articleWebView.getSettings();
                webSettings.setBuiltInZoomControls(true);
                webSettings.setSupportZoom(true);
                articleWebView.loadUrl("https://letanhsang.pythonanywhere.com/blog/dashboard/view/" + slug);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
