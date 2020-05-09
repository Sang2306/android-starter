package com.example.myblog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView articleListView;
    private ImageButton searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        initListViewItems();
    }

    private void setControl(){
        articleListView = findViewById(R.id.articleListView);
        searchBtn = findViewById(R.id.searchBtn);
    }

    private void setEvent(){

    }

    private void initListViewItems(){
        String[] articles = {"Model serialization notes", "How to use Django REST effectively"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.article_listview, R.id.articleTextView, articles);
        articleListView.setAdapter(arrayAdapter);
    }
}
