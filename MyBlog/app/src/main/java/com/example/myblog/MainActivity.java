package com.example.myblog;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myblog.custom.CustomAdapter;
import com.example.myblog.custom.Item;
import com.example.myblog.retrofit.Api;
import com.example.myblog.retrofit.Article;
import com.example.myblog.retrofit.Articles;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private ListView articleListView;
    private EditText searchEditText;
    private Button searchBtn;
    private ProgressDialog progressDialog;
    private ArrayList<Item> articleList = new ArrayList<>();
    private ArrayList<Item> resultList = new ArrayList<>(); //ket qua tim kiem
    private FloatingActionButton floatingAddActionButton;
    public static FloatingActionButton floatingRefreshActionButton;
    private TextView search_result;
    private ListView result_list_view;

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

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                final String textSearch = searchEditText.getText().toString();
                //Tao dialog hien thi ket qua tim kiem
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                ViewGroup viewGroup = findViewById(R.id.mainActivity);
                View dialogSearchResult = LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_result, viewGroup, false);
                builder.setView(dialogSearchResult);

                AlertDialog searchResultDialog = builder.create();
                Objects.requireNonNull(searchResultDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                search_result = dialogSearchResult.findViewById(R.id.search_result);
                result_list_view = dialogSearchResult.findViewById(R.id.result_list_view);

                //gọi api
                Api.getClient().searchArticles(textSearch, new Callback<Articles>() {
                    @Override
                    public void success(Articles articles, Response response) {
                        Article[] marticles = articles.getArticles();

                        resultList.clear();

                        for (Article marticle : marticles) {
                            resultList.add(new Item(marticle.getSlug(), marticle.getUuid(), marticle.getTitle(), marticle.getPublish_date(), R.drawable.logo));
                        }

                        if (textSearch.length() == 0){
                            search_result.setText("Kết quả tìm kiếm 'Tất cả': " + resultList.size());
                        }else{
                            search_result.setText("Kết quả tìm cho '" + textSearch + "': " + resultList.size());
                        }


                        //width, height
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                        //Set CustomAdapter result_list_view
                        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), R.layout.article_listview, resultList, displayMetrics.widthPixels, MainActivity.this);
                        customAdapter.notifyDataSetChanged();
                        result_list_view.setAdapter(customAdapter);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                });
                builder.show();
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
                    articleList.add(new Item(marticle.getSlug(), marticle.getUuid(), marticle.getTitle(), marticle.getPublish_date(), R.drawable.logo));
                }

                //Get displayMetrics for width, height
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                //Set CustomAdapter for list view
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), R.layout.article_listview, articleList, displayMetrics.widthPixels, MainActivity.this);
                articleListView.setAdapter(customAdapter);

                Toast.makeText(MainActivity.this, "Đã tải dữ liệu", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Không thể load dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
