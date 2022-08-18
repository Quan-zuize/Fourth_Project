package com.example.project_4_admin.Viewholder;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4_admin.Interface.ItemClickListener;
import com.example.project_4_admin.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView txtOrderId, txtOrderStatus,txtPhone, txtTime;


    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtPhone = itemView.findViewById(R.id.order_phone);
        txtTime = itemView.findViewById(R.id.order_time);
    }
}
