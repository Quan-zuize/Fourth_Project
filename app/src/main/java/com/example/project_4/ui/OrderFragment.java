package com.example.project_4.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_4.R;
import com.example.project_4.Viewholder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Order;

public class OrderFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Order,OrderViewHolder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");

        recyclerView = root.findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
//        loadOrders(new Buyer().getPhone());
        return root;
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