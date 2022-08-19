package com.example.project_4.Viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4.R;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView txtOrderId, txtOrderStatus,txtSite, txtTime;


    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtSite = itemView.findViewById(R.id.order_site);
        txtTime = itemView.findViewById(R.id.order_time);
    }
}
