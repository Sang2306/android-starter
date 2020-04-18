package com.example.baitap21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
//            something
        }
        setTitle("Login");
        setContentView(R.layout.login);
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        usernameEditText.setText(savedInstanceState.getString("username"));
        passwordEditText.setText(savedInstanceState.getString("password"));
        Toast.makeText(this, "Chuyen trang thai", Toast.LENGTH_SHORT);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("username", usernameEditText.getText().toString());
        outState.putString("password", passwordEditText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public void exit(View view) {
//        finish();
        moveTaskToBack(true);
        android.os.Process.killProcess(Process.myPid());
        System.exit(1);
    }
}
