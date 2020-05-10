package com.example.myblog;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.myblog.custom.CustomAdapter;
import com.example.myblog.custom.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView articleListView;
    private ImageButton searchBtn;
    private ProgressDialog progressDialog;
    private ArrayList<Item> articleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
        //bat dau fetch du lieu
        ArticleFetchAsyncTask loadingArticles = new ArticleFetchAsyncTask();
        loadingArticles.execute();
    }

    private void setControl() {
        articleListView = findViewById(R.id.articleListView);
        searchBtn = findViewById(R.id.searchBtn);
    }

    private void setEvent() {

    }

    /**
     * ArticleFetchAsyncTask Dùng để load dữ liệu bài viết từ api
     */
    @SuppressLint("StaticFieldLeak")
    public class ArticleFetchAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Chờ chút xíu...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            return "null";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //todo JSON Parsing of data from s

            //todo load into arraylist
            articleList.add(new Item(1, "How to use Django REST effectively", R.drawable.logo));
            articleList.add(new Item(2, "QUICK Bookmark", R.drawable.logo));
            articleList.add(new Item(3, "Keep yourself up", R.drawable.logo));
            articleList.add(new Item(4, "Not following perfectionism", R.drawable.logo));
            articleList.add(new Item(4, "Not following perfectionism", R.drawable.logo));
            articleList.add(new Item(4, "Not following perfectionism", R.drawable.logo));
            articleList.add(new Item(4, "Not following perfectionism", R.drawable.logo));
            articleList.add(new Item(4, "Not following perfectionism", R.drawable.logo));
            articleList.add(new Item(4, "Not following perfectionism", R.drawable.logo));
            articleList.add(new Item(4, "Not following perfectionism", R.drawable.logo));
            articleList.add(new Item(4, "Not following perfectionism", R.drawable.logo));
            articleList.add(new Item(4, "Not following perfectionism", R.drawable.logo));
            articleList.add(new Item(4, "Not following perfectionism", R.drawable.logo));
            articleList.add(new Item(4, "Not following perfectionism", R.drawable.logo));

            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), R.layout.article_listview, articleList);
            articleListView.setAdapter(customAdapter);
        }
    }
}
