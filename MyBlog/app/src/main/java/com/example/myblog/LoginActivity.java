package com.example.myblog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.myblog.retrofit.Api;
import com.example.myblog.retrofit.LoginResponse;

import java.util.Objects;
import retrofit.Callback;
import retrofit.RetrofitError;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private EditText usernameEditText;
    private EditText passwordEditText;
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
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        savedPassword = findViewById(R.id.savedPassword);
        loginBtn = findViewById(R.id.loginBtn);
    }

    private void setEvent() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("Đang đăng nhập");
                progressDialog.setMessage("Vui lòng đợi...");
                progressDialog.show();

                Api.getClient().login(
                        username, password, new Callback<LoginResponse>() {
                            @Override
                            public void success(LoginResponse loginResponse, retrofit.client.Response response) {
                                progressDialog.dismiss();
                                LoginResponse loginResponseData = loginResponse;

                                try {
                                    Log.d("USER_FUX", Integer.toString(loginResponseData.getUser()));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(mainActivity);
                                finish();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                progressDialog.dismiss();

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                ViewGroup viewGroup = findViewById(R.id.loginActivity);
                                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.login_failed_dialog, viewGroup, false);
                                builder.setView(dialogView);

                                alertDialog = builder.create();
                                Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                Button okBtn = dialogView.findViewById(R.id.okBtn);

                                alertDialog.show();
                                okBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.hide();
                                    }
                                });
                            }
                        }
                );
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
