package com.example.foodcateringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodcateringapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private TextInputLayout regName,regEmail,regPassword,regPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);

        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPassword);
        regPhone = findViewById(R.id.regPhone);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        String name = regName.getEditText().getText().toString().trim();
        String email = regEmail.getEditText().getText().toString().trim();
        String password = regPassword.getEditText().getText().toString().trim();
        String phone = regPhone.getEditText().getText().toString().trim();

        //Validation
        if(name.isEmpty()){
            regName.setError("Full Name Required!");
            regName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            regEmail.setError("Email Required!");
            regEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regEmail.setError("Please Provide valid email");
            regEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            regPassword.setError("Password Required!");
            regPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            regPassword.setError("Password must be greater than 6 character!");
            regPassword.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            regPhone.setError("Phone Required!");
            regPhone.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(name,email,password,phone);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"User Successfully Registered",Toast.LENGTH_LONG).show();
                                        regName.getEditText().setText("");
                                        regPhone.getEditText().setText("");
                                        regEmail.getEditText().setText("");
                                        regPassword.getEditText().setText("");
                                    }else{
                                        Toast.makeText(RegisterActivity.this,"Failed to Register, Try Again!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(RegisterActivity.this,"Failed to Register, Try Again!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}