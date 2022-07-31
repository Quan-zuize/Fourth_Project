package com.example.project_4.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_4.R;
import com.example.project_4.Viewholder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Buyer;
import model.Order;

public class OrderFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Order,OrderViewHolder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");

//        loadOrders(new Buyer().getPhone());
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

//    private void loadOrders(String phone) {
//        adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(
//                Order.class,
//                R.layout.fragment_order,
//                OrderViewHolder.class,
//                requests
//        ) {
//            @NonNull
//            @Override
//            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {
//
//            }
//
//
//            protected void populateViewHolder(OrderViewHolder viewHolder, Order model, int position) {
//                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
//            }
//        };
//        myRecyclerView.setAdapter(adapter);
//    }
}