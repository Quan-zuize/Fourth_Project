package com.example.project_4.service;

import com.example.project_4.model.Token;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

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
        Token token = new Token(tokenRefreshed,false);
        tokens.child("Buyer_ID").setValue(token);
    }
}
