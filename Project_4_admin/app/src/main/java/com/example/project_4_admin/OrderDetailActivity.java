package com.example.project_4_admin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4_admin.adapters.OrderDetailsAdapter;
import com.example.project_4_admin.model.OrderDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public TextView textMess, textTotal;

    FirebaseDatabase database;
    DatabaseReference requests;
    FirebaseUser user;

    List<OrderDetail> listDetails = new ArrayList<>();

    int orderID;
    String mess;
    Float total;

    OrderDetailsAdapter orderDetailsAdapter;

    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");

        orderID = (Integer) getIntent().getSerializableExtra("orderID");
        mess = getIntent().getSerializableExtra("note").toString();
        total = Float.parseFloat(getIntent().getSerializableExtra("total").toString());

        getListOrderDetails();

        recyclerView = findViewById(R.id.listCart);
        textMess = findViewById(R.id.textNote);
        textTotal = findViewById(R.id.totalTxt);

        textMess.setText("Tin nhắn :" + " " + mess);
        textTotal.setText(String.format("%,.0f", total)+ " VNĐ");

        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void getListOrderDetails() {
        Query query = requests.orderByChild("orderID").equalTo(orderID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    listDetails.clear();
                    requests.child(childSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.child("details").getChildren()) {
                                OrderDetail orderDetail = data.getValue(OrderDetail.class);
                                listDetails.add(orderDetail);
                            }
                            loadListOrder();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadListOrder() {
        linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        orderDetailsAdapter = new OrderDetailsAdapter(listDetails, this.getBaseContext());
        orderDetailsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(orderDetailsAdapter);
    }
}