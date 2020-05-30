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
        String slug = intent.getStringExtra("slug");

        articleWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings =  articleWebView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        articleWebView.loadUrl("https://letanhsang.pythonanywhere.com/blog/dashboard/view/" + slug);
    }

    private void setControl(){
        articleWebView = findViewById(R.id.articleWebView);
    }
}
