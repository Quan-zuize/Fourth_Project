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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    TextView registerNowBtn, forgetBtn;
    Button loginBtn;

    FirebaseAuth auth;
    DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");

    SharedPreferences sharedPreferences;
    static String SHARED_PREF_NAME = "myPref";
    static String KEY_PASS = "pass";
    static String KEY_EMAIL = "email";
    static String KEY_NAME = "name";
    static String KEY_PHONE = "phone";
    static String KEY_ID = "id";
    static String KEY_SITE = "site";

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

        sharedPreferences = getBaseContext().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
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
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        assert user != null;
                        editor.putString(KEY_ID, user.getUid());
                        editor.putString(KEY_PASS, pass);
                        editor.putString(KEY_EMAIL, email);

                        referenceUsers.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                editor.putString(KEY_NAME, snapshot.child("name").getValue().toString());
                                editor.putString(KEY_PHONE, snapshot.child("phone").getValue().toString());

                                if (snapshot.child("role").getValue(String.class).equals("manager")) {
                                    editor.putString(KEY_SITE, snapshot.child("site_id").toString());
                                    Toast.makeText(LoginActivity.this, "Login Successfully ", Toast.LENGTH_LONG).show();
                                    editor.apply();
//                                    startActivity(new Intent(LoginActivity.this, Manager_dashboardActivity.class));
                                } else if (snapshot.child("role").getValue(String.class).equals("buyer")) {
                                    Toast.makeText(LoginActivity.this, "Login Successfully ", Toast.LENGTH_LONG).show();
                                    editor.apply();
                                    startActivity(new Intent(LoginActivity.this, Store_dashboardActivity.class));
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