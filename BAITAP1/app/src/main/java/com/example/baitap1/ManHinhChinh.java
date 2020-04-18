package com.example.baitap1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ManHinhChinh extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        Log.d("SANG-PAUSE", "man hinh chinh bi tam dung do no da chuyen sang man hinh khac");
        super.onPause();
    }

    public void startScreen1(View view) {
        Intent intent = new Intent(this, ManHinh1.class);
        startActivity(intent);
    }

    public void startScreen2(View view) {
        Intent intent = new Intent(this, ManHinh2.class);
        startActivity(intent);
    }
}
