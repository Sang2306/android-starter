package com.example.myblog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.myblog.database.DBAdapter;
import com.example.myblog.database.User;
import com.example.myblog.retrofit.Api;
import com.example.myblog.retrofit.LoginResponse;

import java.util.ArrayList;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;

public class LoginActivity extends AppCompatActivity {

    //key cho share preferences
    public static final String LOGIN_PREF = "myblog";
    public static final String USERID = "userid";

    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox savedPassword;
    private Button loginBtn;

    private DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
        setEvent();

        dbAdapter = new DBAdapter(this);
        loadLoginInfoFromDB();
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
                final String username = usernameEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("Đang đăng nhập");
                progressDialog.setMessage("Vui lòng đợi...");
                progressDialog.show();

                Api.getClient().login(
                        username, password, new Callback<LoginResponse>() {
                            @Override
                            public void success(LoginResponse loginResponse, retrofit.client.Response response) {
                                progressDialog.dismiss();

                                //lay user id va luu vao trong SharedPreferences
                                int user = loginResponse.getUser();
                                Log.d("OWNER_LOG", Integer.toString(user));
                                SharedPreferences sp = getSharedPreferences(LOGIN_PREF, Context.MODE_PRIVATE);
                                sp.edit().putInt(USERID, user).apply();

                                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(mainActivity);

                                if (savedPassword.isChecked()) {
                                    dbAdapter.insertData(username, password);
                                }

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
    }

    private void loadLoginInfoFromDB() {
        ArrayList<User> users = dbAdapter.getData();
        try {
            //thông tin lần đăng nhập gần nhất
            usernameEditText.setText(users.get(users.size()-1).getUsername());
            passwordEditText.setText(users.get(users.size()-1).getPassword());
        } catch (Exception ignored) {
        }
    }
}
