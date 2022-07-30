package com.example.project_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Buyer;

public class SignUpActivity extends AppCompatActivity {
    EditText fullname, email, phone, password, conPassword;
    Button loginNowBtn;
    TextView registerBtn, forgetBtn;
    FirebaseAuth auth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shopdemo-8c277-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        conPassword = findViewById(R.id.conPassword);
        registerBtn = findViewById(R.id.sign_up_button);
        loginNowBtn = findViewById(R.id.sign_in_button);
        forgetBtn = findViewById(R.id.btn_reset_password);
        auth = FirebaseAuth.getInstance();
        registerBtn.setOnClickListener(view -> {
            createUser();
        });
        loginNowBtn.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        });
        forgetBtn.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
        });
    }

    private void createUser() {
        String fullname = this.fullname.getText().toString();
        String email = this.email.getText().toString();
        String phone = this.phone.getText().toString();
        String pass = password.getText().toString();
        String conPass = this.conPassword.getText().toString();
        if (fullname.isEmpty()) {
            this.fullname.setError("Full Name is required !");
            this.fullname.requestFocus();
        } else if (email.isEmpty()) {
            this.email.setError("Email is required !");
            this.email.requestFocus();
        } else if (phone.isEmpty()) {
            this.phone.setError("Phone is required !");
            this.phone.requestFocus();
        } else if (pass.isEmpty()) {
            this.password.setError("Pass is required !");
            this.password.requestFocus();
        } else if (conPass.isEmpty()) {
            this.conPassword.setError("Confirm is required !");
            this.conPassword.requestFocus();
        } else if (!conPass.equals(pass)) {
            this.conPassword.setError("Password Are Not Matching !");
            this.conPassword.requestFocus();
        } else {
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "User Registered Successfully ", Toast.LENGTH_SHORT).show();
                        //insert to db
                        Buyer userObject = new Buyer(fullname, email, phone);
                        databaseReference.child("Users").child(phone).setValue(userObject);
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(SignUpActivity.this, "Registered Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}