package com.example.project_4_admin.service;

import com.example.project_4_admin.model.Token;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseService extends FirebaseInstanceIdService {
    String tokenRefreshed;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        tokenRefreshed = FirebaseInstanceId.getInstance().getToken();
        updateTokenToFireBase();
    }

    private void updateTokenToFireBase(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Token");
        Token token = new Token(tokenRefreshed,true);
        tokens.child("Buyer_ID").setValue(token);
    }
}
