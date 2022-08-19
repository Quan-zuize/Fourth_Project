package com.example.project_4.ui;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project_4.LoginActivity;
import com.example.project_4.R;
import com.example.project_4.SignUpActivity;
import com.example.project_4.Store_dashboardActivity;
import com.example.project_4.UpdatePassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class ProfileFragment extends Fragment {


    TextInputEditText fullName_edit_text;
    TextInputEditText email_edit_text;
    TextInputEditText phone_edit_text;
    String name, email, phone;

    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    LinearLayout update_container;
    Button btn_logout, btn_update, btn_login, btn_signup, btn_changePass;

    SharedPreferences sharedPreferences;
    static String SHARED_PREF_NAME = "myPref";
    static String KEY_EMAIL = "email";
    static String KEY_NAME = "name";
    static String KEY_PHONE = "phone";
    static String KEY_PASS = "pass";
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email = sharedPreferences.getString(KEY_EMAIL, null);
        name = sharedPreferences.getString(KEY_NAME, null);
        phone = sharedPreferences.getString(KEY_PHONE, null);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        fullName_edit_text = root.findViewById(R.id.name_text);
        email_edit_text = root.findViewById(R.id.email_text);
        phone_edit_text = root.findViewById(R.id.phone_text);

        btn_login = root.findViewById(R.id.loginBtn);
        btn_signup = root.findViewById(R.id.signupBtn);
        btn_logout = root.findViewById(R.id.LogOutBtn);
        btn_update = root.findViewById(R.id.UpdateBtn);
        btn_changePass = root.findViewById(R.id.UpdatePassBtn);
        update_container = root.findViewById(R.id.profile_container);

        checkLogin();
        loadInformation();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignUpActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getContext(), Store_dashboardActivity.class));
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        btn_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UpdatePassword.class));
            }
        });
        return root;
    }

    private void loadInformation() {
        fullName_edit_text.setText(name);
        email_edit_text.setText(email);
        phone_edit_text.setText(phone);
    }

    private void update() {
        if (isFullNameChanged() || isEmailChanged() || isPhoneChanged()) {
            Toast.makeText(getContext(), "Data has been updated", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Same data can not update", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isFullNameChanged() {
        if (!name.equals(fullName_edit_text.getText().toString())) {
            databaseReference.child(firebaseUser.getUid()).child("name").setValue(fullName_edit_text.getText().toString());
            name = fullName_edit_text.getText().toString();
            editor.putString(KEY_NAME, name);
            editor.apply();
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmailChanged() {
        if (!email.equals(email_edit_text.getText().toString())) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                email_edit_text.setError("Invalid Email !");
                email_edit_text.requestFocus();
                return false;
            } else {
                reAuthenticate();
                databaseReference.child(firebaseUser.getUid()).child("email").setValue(email_edit_text.getText().toString());
                email = email_edit_text.getText().toString();
                editor.putString(KEY_EMAIL, email);
                editor.apply();
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean isPhoneChanged() {
        String pattern = "(0[3|5|7|8|9])+([0-9]{8})";
        Pattern r = Pattern.compile(pattern);

        if (!phone.equals(phone_edit_text.getText().toString())) {
            if (!r.matcher(phone_edit_text.getText().toString().trim()).find()) {
                phone_edit_text.setError("Invalid Phone !");
                phone_edit_text.requestFocus();
                return false;
            } else {
                databaseReference.child(firebaseUser.getUid()).child("phone").setValue(phone_edit_text.getText().toString());
                phone = phone_edit_text.getText().toString();
                editor.putString(KEY_PHONE, phone);
                editor.apply();
                return true;
            }
        } else {
            return false;
        }
    }

    private void checkLogin() {
        String phone = sharedPreferences.getString(KEY_PHONE, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        if (phone == null || email == null) {
            btn_login.setVisibility(View.VISIBLE);
            btn_signup.setVisibility(View.VISIBLE);
            update_container.setVisibility(View.GONE);
        } else {
            btn_login.setVisibility(View.GONE);
            btn_signup.setVisibility(View.GONE);
            update_container.setVisibility(View.VISIBLE);
        }
    }

    private void reAuthenticate() {
        String strNewEmail = email_edit_text.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, sharedPreferences.getString(KEY_PASS, null));

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updateEmail(strNewEmail)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "User email address updated", Toast.LENGTH_SHORT).show();
                                                //mMainActivity.showUserInformation();
                                            } else {

                                            }
                                        }
                                    });
                        } else {
                            Log.d(TAG, "Error auth failed");
                        }
                    }
                });
    }
}