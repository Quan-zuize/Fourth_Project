package com.example.project_4;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePassword extends AppCompatActivity {

    private TextInputEditText txtOldPassword, txtNewPassword, txtRetypePassword;
    private Button btnUpdate , btn_reset_password;
    private DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    SharedPreferences sharedPreferences;


    static String SHARED_PREF_NAME = "myPref";
    static String KEY_PASS = "pass";

    String pass;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass_word);
        txtOldPassword = findViewById(R.id.txtOldPassword);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtRetypePassword = findViewById(R.id.txtRetypePassword);

        btnUpdate = findViewById(R.id.btnUpdate);
        btn_reset_password = findViewById(R.id.btn_reset_password);

        sharedPreferences = getBaseContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdatePassword.this, ResetPasswordActivity.class));
            }
        });

        btnUpdate.setOnClickListener((View view) -> {
            if (!txtNewPassword.getText().toString().equals(txtRetypePassword.getText().toString())) {
                Toast.makeText(UpdatePassword.this, "Password and retype pass must be the same", Toast.LENGTH_SHORT).show();
                return;
            }
            AuthCredential credential = EmailAuthProvider
                    .getCredential(firebaseUser.getEmail(), txtOldPassword.getText().toString());
            if (credential == null) {
                Toast.makeText(UpdatePassword.this, "Wrong old password", Toast.LENGTH_SHORT).show();
                return;
            }
            firebaseUser.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                firebaseUser.updatePassword(txtNewPassword.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
//                                            Log.d(TAG, "Password updated");
                                                    Toast.makeText(UpdatePassword.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(UpdatePassword.this, "Error password not updated", Toast.LENGTH_SHORT).show();
//                                            Log.d(TAG, "Error password not updated");
                                                }
                                            }
                                        });
                            } else {
                                Log.d(TAG, "Error auth failed");
                            }
                        }
                    });
        });
    }

}