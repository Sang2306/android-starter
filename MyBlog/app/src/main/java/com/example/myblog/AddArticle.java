package com.example.myblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import in.nashapp.androidsummernote.Summernote;

public class AddArticle extends AppCompatActivity {

    private Summernote summernote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    private void setControl(){
        summernote = findViewById(R.id.summernote);
    }
}
