package com.example.project_4.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_4.R;
import com.example.project_4.Viewholder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import model.Order;
import model.User;

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
        loadOrders(new User().getName());
        return root;
    }

    private void loadOrders(String name) {
        Query query = requests.orderByChild("Buyer Name").equalTo(name);

        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(query, Order.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {
            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.order_layout for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_layout, parent, false);
                return new OrderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {
                holder.txtOrderId.setText(adapter.getRef(position).getKey());
                holder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                holder.txtSite.setText(model.getSite_address());
                holder.txtTime.setText(model.getTimeOrder().toString());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(int status) {
        String rs = "";
        switch (status){
            case 0:
                rs = "Đơn hủy";
                break;
            case 1:
                rs = "Đơn chờ xác nhận";
                break;
            case 2:
                rs = "Đơn đã nhận";
                break;
            case 3:
                rs = "Đã thanh toán";
                break;
            default:
                break;
        }
        return rs;
    }
}