package com.example.project_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    TextView registerNowBtn,forgetBtn;
    Button loginBtn;

    FirebaseAuth auth;

    SharedPreferences sharedPreferences;
    private static String SHARED_PREF_NAME = "myPref";
    private static String KEY_PASS = "pass";
    private static String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgetBtn = findViewById(R.id.btn_reset_password);
        registerNowBtn = findViewById(R.id.btn_signup);
        loginBtn = findViewById(R.id.btn_login);

        auth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        loginBtn.setOnClickListener(view -> {
            loginUser();
        });

        registerNowBtn.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
        forgetBtn.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
        });
    }

    private void loginUser() {
        String email = this.email.getText().toString();
        String pass = this.password.getText().toString();
        if (TextUtils.isEmpty(email)) {
            this.email.setError("Email is required !");
            this.email.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            this.password.setError("Pass is required !");
            this.password.requestFocus();
        } else {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_PASS,pass);
                        editor.putString(KEY_EMAIL,email);
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this, Store_dashboardActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Log in Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}