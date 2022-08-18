package com.example.project_4.adapters;

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
import com.example.project_4.Viewholder.OrderDetailsHolder;
import com.example.project_4.Viewholder.OrderViewHolder;
import com.example.project_4.model.OrderDetail;
import com.google.firebase.database.Query;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsHolder>{
    List<OrderDetail> orderDetailList;
    Context context;

    public OrderDetailsAdapter(List<OrderDetail> orderDetailList, Context context) {
        this.orderDetailList = orderDetailList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.order_details_layout, parent, false);
        return new OrderDetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsHolder holder, int position) {
        holder.txtName.setText(orderDetailList.get(position).getMenuTitle());
        holder.txtPrice.setText(String.format("%,.0f",orderDetailList.get(position).getPrice()));
        holder.txtQuantity.setText(String.valueOf(orderDetailList.get(position).getQuantity()));
        holder.txtTotal.setText(String.format("%,.0f",orderDetailList.get(position).getTotal()));
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }
}
