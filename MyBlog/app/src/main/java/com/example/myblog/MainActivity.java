package com.example.myblog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myblog.custom.CustomAdapter;
import com.example.myblog.custom.Item;
import com.example.myblog.retrofit.Api;
import com.example.myblog.retrofit.Article;
import com.example.myblog.retrofit.Articles;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;

public class MainActivity extends AppCompatActivity {

    private ListView articleListView;
    private EditText searchEditText;
    private Button searchBtn;
    private ProgressDialog progressDialog;
    private ArrayList<Item> articleList = new ArrayList<>();
    private FloatingActionButton floatingAddActionButton;
    private FloatingActionButton floatingRefreshActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
        loadData();
    }

    private void setControl() {
        articleListView = findViewById(R.id.articleListView);
        searchBtn = findViewById(R.id.searchBtn);
        floatingAddActionButton = findViewById(R.id.floatingAddActionButton);
        floatingRefreshActionButton = findViewById(R.id.floatingRefreshActionButton);
        searchEditText = findViewById(R.id.searchEditText);
    }

    private void setEvent() {
        floatingAddActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addArticleActivity = new Intent(getApplicationContext(), AddArticle.class);
                addArticleActivity.putExtra("title", "Tiêu đề");
                addArticleActivity.putExtra("html", "Nội dung");
                startActivity(addArticleActivity);
            }
        });

        floatingRefreshActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {
        // Xóa dữ liệu cũ
        articleList.clear();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Api.getClient().listArticles(new Callback<Articles>() {
            @Override
            public void success(Articles articles, retrofit.client.Response response) {
                progressDialog.dismiss();

                Article[] marticles = articles.getArticles();
                for (Article marticle : marticles) {
                    articleList.add(new Item(marticle.getUuid(), marticle.getTitle(), marticle.getPublish_date(), R.drawable.logo));
                }

                //Get displayMetrics for width, height
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                //Set CustomAdapter for list view
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), R.layout.article_listview, articleList, displayMetrics.widthPixels, MainActivity.this);
                articleListView.setAdapter(customAdapter);

                Toast.makeText(MainActivity.this, "Đã tải dữ liệu", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Không thể load dữ liệu", Toast.LENGTH_LONG).show();
            }
        });
    }
}
