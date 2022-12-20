package com.example.foodcateringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout txtEmail,txtPassword;
    private Button btnSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignIn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = txtEmail.getEditText().getText().toString().trim();
        String password = txtPassword.getEditText().getText().toString().trim();

        // Validate data First
        if(email.isEmpty()){
            txtEmail.setError("Email is required!");
            txtEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError("Must be in Email format");
            txtEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            txtPassword.setError("Password is required!");
            txtPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            txtPassword.setError("Password too short!");
            txtPassword.requestFocus();
            return;
        }

        // Signin Attempt
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Redirect to homepage
                    Intent home = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(home);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Failed Login! User Credentials Invalid!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}