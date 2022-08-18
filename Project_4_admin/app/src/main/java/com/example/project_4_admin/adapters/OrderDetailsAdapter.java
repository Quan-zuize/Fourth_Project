package com.example.project_4_admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4_admin.R;
import com.example.project_4_admin.Viewholder.OrderDetailsHolder;
import com.example.project_4_admin.model.OrderDetail;

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
