package com.example.myblog.custom;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.myblog.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Item> {
    private ArrayList<Item> articleList = new ArrayList<>();
    private int screenWidth;

    public CustomAdapter(Context context, int textViewSourceId, ArrayList<Item> objects, int screenWidth) {
        super(context, textViewSourceId, objects);
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        v = inflater.inflate(R.layout.article_listview, null);
        //Add controls
        CardView articleCardView = v.findViewById(R.id.articleCardView);
        CardView articleCardViewForButton = v.findViewById(R.id.articleCardViewForButton);
        TextView articleTextView = v.findViewById(R.id.articleTextView);
        ImageView articleImageVIew = v.findViewById(R.id.articleImageVIew);
        Button modifyBtn = v.findViewById(R.id.modifyBtn);
        Button deleteBtn = v.findViewById(R.id.deleteBtn);
        //Set width, height for articleCardView
        articleCardView.setLayoutParams(new LinearLayout.LayoutParams(this.screenWidth, 120));
        //Set values
        articleTextView.setText(articleList.get(position).getTitle());
        articleImageVIew.setImageResource(articleList.get(position).getImageResource());
        articleTextView.setTag(articleList.get(position).getId());
        modifyBtn.setTag(articleList.get(position).getId());
        deleteBtn.setTag(articleList.get(position).getId());
        //Add events
        articleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Đã click vào xem và bài viết có id " + v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Đã click vào nút sửa và bài viết có id " + v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Đã click vào nút xóa và bài viết có id " + v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
