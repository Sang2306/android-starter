package com.example.myblog;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myblog.custom.CustomAdapter;
import com.example.myblog.custom.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://letanhsang.pythonanywhere.com/blog/dashboard/list/")
                    .method("GET", null)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "null";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //todo JSON Parsing of data from s
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray articles = jsonObject.getJSONArray("articles");
                for (int i = 0; i < articles.length(); i++){
                    JSONObject article = articles.getJSONObject(i);
                    articleList.add(new Item(article.get("uuid").toString(), article.get("title").toString(), R.drawable.logo));
                }
                Log.d("JSON", articles.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //Get displayMetrics for width, height
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            //Set CustomAdapter for list view
            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), R.layout.article_listview, articleList, displayMetrics.widthPixels);
            articleListView.setAdapter(customAdapter);
        }
    }
}
