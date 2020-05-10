package com.example.myblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private CheckBox savedPassword;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
        setEvent();
    }

    private void setControl() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        savedPassword = findViewById(R.id.savedPassword);
        loginBtn = findViewById(R.id.loginBtn);
    }

    private void setEvent() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });

        savedPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                todo save username and password to SharePreferences
            }
        });
    }
}
