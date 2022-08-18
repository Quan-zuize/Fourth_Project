package com.example.project_4_admin.Viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4_admin.R;

public class OrderDetailsHolder extends RecyclerView.ViewHolder {
    public TextView txtName, txtPrice,txtQuantity, txtTotal;

    public OrderDetailsHolder(@NonNull View itemView) {
        super(itemView);

        txtName = itemView.findViewById(R.id.textName);
        txtPrice = itemView.findViewById(R.id.textPrice);
        txtQuantity = itemView.findViewById(R.id.textQuantity);
        txtTotal = itemView.findViewById(R.id.textTotal);
    }
}
