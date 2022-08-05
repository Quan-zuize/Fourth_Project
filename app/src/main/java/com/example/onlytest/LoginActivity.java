package com.example.onlytest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlytest.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    TextView registerNowBtn;
    TextView forgetBtn;
    Button loginBtn;

    FirebaseAuth auth;

    DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference().child("Users");

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
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        referenceUsers.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.child("type").getValue(String.class).equals("admin")) {
                                    Toast.makeText(LoginActivity.this, "Login Successfully 1111" + email, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(LoginActivity.this, UpdateProfileActivity.class));
                                } else if (snapshot.child("type").getValue(String.class).equals("user")) {
                                    Toast.makeText(LoginActivity.this, "Login Successfully 0000" + email, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Log in Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
