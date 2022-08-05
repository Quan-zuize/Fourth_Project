package com.example.onlytest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText fullName_edit_text;
    private EditText email_edit_text;
    private EditText phone_edit_text;

//    String FULLNAME = null , EMAIL  = null, PHONE = null;

    String email;

    private DatabaseReference databaseReference;

    private FirebaseDatabase database;

    Button btn_logout , btn_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

//        showData();

        Intent intent = getIntent();

        email = intent.getStringExtra("email");

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        fullName_edit_text = (EditText) findViewById(R.id.name_text);
//        email_edit_text = (EditText)findViewById(R.id.email_text);
//        phone_edit_text = (EditText)findViewById(R.id.phone_text);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("email").getValue().equals(email)){
                        fullName_edit_text.setText(dataSnapshot.child("fullname").getValue(String.class));
//                        email_edit_text.setText(dataSnapshot.child("email").getValue(String.class));
//                        phone_edit_text.setText(dataSnapshot.child("phone").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void showData(){
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        String name =  user.getDisplayName();
//        String email = user.getEmail();
//        String phone = user.getPhoneNumber();

//        fullName_edit_text.setText(name);
//        email_edit_text.setText(email);
//        phone_edit_text.setText(phone);

//        Intent intent = getIntent();
//        FULLNAME = intent.getStringExtra("fullname");
//        EMAIL = intent.getStringExtra("email");
//        PHONE = intent.getStringExtra("phone");
//
//        fullName_edit_text.setText(FULLNAME);
//        email_edit_text.setText(EMAIL);
//        phone_edit_text.setText(PHONE);
//    }

//    public void update(View view){
//        if(isFullNameChanged() ){
//            Toast.makeText(this , "Data has been updated" , Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private boolean isFullNameChanged(){
//        if(!FULLNAME.equals(fullName_edit_text.getText().toString())){
//            databaseReference.child(FULLNAME).child("fullname").setValue(fullName_edit_text.getText().toString());
//            FULLNAME = fullName_edit_text.getText().toString();
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    private boolean isEmailChanged(){
//        if(!EMAIL.equals(email_edit_text.getText().toString())){
//            databaseReference.child(EMAIL).child("email").setValue(email_edit_text.getText().toString());
//            EMAIL = email_edit_text.getText().toString();
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    private boolean isPhoneChanged(){
//        if(!PHONE.equals(phone_edit_text.getText().toString())){
//            databaseReference.child(PHONE).child("phone").setValue(phone_edit_text.getText().toString());
//            PHONE = phone_edit_text.getText().toString();
//            return true;
//        }else {
//            return false;
//        }
//    }


}