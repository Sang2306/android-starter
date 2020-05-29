package com.example.myblog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myblog.retrofit.Api;
import com.example.myblog.retrofit.CreateArticleResponse;
import com.example.myblog.retrofit.UpdateArticleResponse;

import in.nashapp.androidsummernote.Summernote;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.myblog.LoginActivity.LOGIN_PREF;
import static com.example.myblog.LoginActivity.USERID;

public class AddArticle extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Summernote summernote;
    private Button saveBtn;
    private Button deleteBtn;
    private EditText editTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setControl();
        setEvent();

        String title = getIntent().getStringExtra("title");
        String html = getIntent().getStringExtra("html");
        editTextTitle.setText(title);
        assert html != null;
        summernote.setText(html);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void setControl() {
        summernote = findViewById(R.id.summernote);
        editTextTitle = findViewById(R.id.article_title_input);
        saveBtn = findViewById(R.id.saveBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
    }

    private void setEvent() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uuid = getIntent().getStringExtra("uuid");
                String title = editTextTitle.getText().toString().trim();
                String html = summernote.getText();

                //lay userid tu SharedPreferences
                SharedPreferences sp = getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
                int owner = sp.getInt(USERID, 0);

                Log.d("OWNER", Integer.toString(owner));

                progressDialog = new ProgressDialog(AddArticle.this);
                progressDialog.setTitle("Đang lưu");
                progressDialog.setMessage("Vui lòng đợi...");
                progressDialog.show();

                //Goi Api de luu lai bai viet
                if (uuid == null) {
                    Api.getClient().createAnArticle(title, html, html, owner, new Callback<CreateArticleResponse>() {
                        @Override
                        public void success(CreateArticleResponse createArticleResponse, Response response) {
                            progressDialog.dismiss();
                            if (createArticleResponse.isCreated()) {
                                Toast.makeText(AddArticle.this, "Đã tạo bài viết thành công!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            progressDialog.dismiss();
                            Toast.makeText(AddArticle.this, "Tạo bài viết không thành công!", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Api.getClient().updateAnArticle(uuid, title, html, html, owner, new Callback<UpdateArticleResponse>() {
                        @Override
                        public void success(UpdateArticleResponse updateArticleResponse, Response response) {
                            progressDialog.dismiss();
                            if (updateArticleResponse.isUpdate()) {
                                Toast.makeText(AddArticle.this, "Cập nhệt bài viết thành công!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            progressDialog.dismiss();
                            Toast.makeText(AddArticle.this, "Không thể cập nhật bài viết!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                summernote.setText("");
                editTextTitle.setText("");
            }
        });
    }
}
