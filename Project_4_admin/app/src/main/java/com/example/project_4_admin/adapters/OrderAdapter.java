package com.example.project_4_admin.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4_admin.Common.Common;
import com.example.project_4_admin.DetailsActivity;
import com.example.project_4_admin.OrderDetailActivity;
import com.example.project_4_admin.R;
import com.example.project_4_admin.Viewholder.OrderViewHolder;
import com.example.project_4_admin.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;



public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    List<Order> orderList;
    Context context;

    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.order_layout, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtOrderId.setText("#" + orderList.get(position).getOrderID());
        holder.txtOrderStatus.setText(Common.convertCodeToStatus(orderList.get(position).getStatus()));
        holder.txtPhone.setText(orderList.get(position).getBuyerPhone());
        holder.txtTime.setText(orderList.get(position).getTimeOrder());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(holder.itemView.getContext(), OrderDetailActivity.class);
//                //intent.putExtra("object",orderList.get(position));
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
