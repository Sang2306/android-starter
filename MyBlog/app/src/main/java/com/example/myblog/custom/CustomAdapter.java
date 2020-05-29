package com.example.myblog.custom;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.myblog.AddArticle;
import com.example.myblog.MainActivity;
import com.example.myblog.R;
import com.example.myblog.ViewArticle;
import com.example.myblog.retrofit.Api;
import com.example.myblog.retrofit.Article;
import com.example.myblog.retrofit.Articles;
import com.example.myblog.retrofit.DeleteArticleResponse;

import java.util.ArrayList;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CustomAdapter extends ArrayAdapter<Item> {
    private ArrayList<Item> articleList = new ArrayList<>();
    private int screenWidth;

    //Context
    private Context context;

    //tao dialog de hien thi confirm xoa bai viet
    private AlertDialog alertDialog;

    public CustomAdapter(Context context, int textViewSourceId, ArrayList<Item> objects, int screenWidth, Context _this) {
        super(context, textViewSourceId, objects);
        this.context = _this;
        articleList = objects;
        this.screenWidth = screenWidth;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        v = inflater.inflate(R.layout.article_listview, null);
        //Add controls
        final CardView articleCardView = v.findViewById(R.id.articleCardView);
        CardView articleCardViewForButton = v.findViewById(R.id.articleCardViewForButton);
        final TextView articleTextView = v.findViewById(R.id.articleTextView);
        TextView articleDateTextView = v.findViewById(R.id.articleDateTextView);
        ImageView articleImageVIew = v.findViewById(R.id.articleImageVIew);
        Button modifyBtn = v.findViewById(R.id.modifyBtn);
        Button deleteBtn = v.findViewById(R.id.deleteBtn);
        //Set width, height for articleCardView
        articleCardView.setLayoutParams(new LinearLayout.LayoutParams(this.screenWidth, LinearLayout.LayoutParams.MATCH_PARENT));
        //Set values
        articleTextView.setText(articleList.get(position).getTitle());
        articleDateTextView.setText(articleList.get(position).getDateText());
        articleImageVIew.setImageResource(articleList.get(position).getImageResource());
        articleTextView.setTag(articleList.get(position).getId());
        modifyBtn.setTag(articleList.get(position).getId());
        deleteBtn.setTag(articleList.get(position).getId());
        //Add events
        articleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articleTextView.performClick();
            }
        });

        articleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewArticle = new Intent(getContext(), ViewArticle.class);
                viewArticle.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String uuid = v.getTag().toString();
                viewArticle.putExtra("uuid", uuid);
                getContext().startActivity(viewArticle);
            }
        });

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uuid = v.getTag().toString();
                Api.getClient().getArticle(uuid, new Callback<Article>() {
                    @Override
                    public void success(Article article, Response response) {
                        Intent editActivity = new Intent(context.getApplicationContext(), AddArticle.class);
                        editActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        editActivity.putExtra("uuid", uuid);
                        editActivity.putExtra("title", article.getTitle());
                        editActivity.putExtra("html", article.getHtml());
                        context.getApplicationContext().startActivity(editActivity);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lay uuid
                final String uuid = v.getTag().toString();

                try {
                    // hien thi dialog xac nhan xoa
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    ViewGroup viewGroup = v.findViewById(R.id.mainActivity);
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.delete_article_confirm, viewGroup, false);
                    builder.setView(dialogView);

                    alertDialog = builder.create();
                    Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // thiet lap dieu khien cho cac nut
                    Button hideBtn = dialogView.findViewById(R.id.cancelBtn);
                    Button okBtn = dialogView.findViewById(R.id.okBtn);
                    alertDialog.show();

                    hideBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.hide();
                        }
                    });
                    // thiet lap su kien cho cac nut
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Api.getClient().deleteArticle(uuid, new Callback<DeleteArticleResponse>() {
                                @Override
                                public void success(DeleteArticleResponse deleteArticleResponse, Response response) {
                                    if (deleteArticleResponse.isDeleted()) {
                                        Toast.makeText(context, "Đã xóa bài viết rồi nha", Toast.LENGTH_LONG).show();
                                        alertDialog.dismiss();
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Toast.makeText(context, "Không thể xóa bài viết", Toast.LENGTH_LONG).show();
                                    alertDialog.dismiss();
                                }
                            });

                        }
                    });

                } catch (Exception e) {
                    Log.d("LOI", Objects.requireNonNull(e.getMessage()));
                }
            }
        });
        return v;
    }
}
