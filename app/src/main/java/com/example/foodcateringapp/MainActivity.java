package com.example.foodcateringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegister;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnRegister:
                    Intent register = new Intent(MainActivity.this,RegisterActivity.class);
                    startActivity(register);
                    break;
                case R.id.btnLogin:
                    Intent login = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(login);
                    break;
            }
    }
}