package com.example.project_4_admin.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.project_4_admin.Common.Common;
import com.example.project_4_admin.Manager_dashboardActivity;
import com.example.project_4_admin.R;
import com.example.project_4_admin.Store_dashboardActivity;
import com.example.project_4_admin.model.Order;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class ListenOrder extends Service implements ChildEventListener {
    FirebaseDatabase database;
    DatabaseReference reference;

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Order");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        reference.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Order order = snapshot.getValue(Order.class);
        assert order != null;
        if(order.getStatus() ==  1){
            showNotification(snapshot.getKey(), order);
        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Order order = snapshot.getValue(Order.class);
        showNotification(snapshot.getKey(),order);
    }

    private void showNotification(String key, Order order) {
        Intent intent = new Intent(getBaseContext(), Manager_dashboardActivity.class);
        intent.putExtra("userName",order.getBuyer_name());
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Anya")
                .setContentInfo("New Order")
                .setContentText("You have new order #"+key)
                .setContentIntent(contentIntent)
                .setContentInfo("Info")
                .setSmallIcon(R.drawable.ic_shopping_cart_white_24);

        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        int randomInt = new Random().nextInt(9999-1)+1;
        notificationManager.notify(randomInt,builder.build());
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}