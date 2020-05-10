package com.example.myblog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ArticleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_listview);
    }
}
