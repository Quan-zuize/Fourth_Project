package com.example.project_4.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4.Common.Common;
import com.example.project_4.OrderDetailActivity;
import com.example.project_4.R;
import com.example.project_4.Viewholder.OrderViewHolder;
import com.example.project_4.model.Order;

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
        holder.txtSite.setText(orderList.get(position).getSiteAddress());
        if(orderList.get(position).getStatus() == 3){
            holder.txtTime.setText("Thời gian : " + orderList.get(position).getTimeTaken());
        }else {
            holder.txtTime.setText("Thời gian : " + orderList.get(position).getTimeOrder());

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), OrderDetailActivity.class);
                intent.putExtra("orderID",  orderList.get(position).getOrderID());
                intent.putExtra("note",  orderList.get(position).getNote());
                intent.putExtra("total", orderList.get(position).getTotal());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
