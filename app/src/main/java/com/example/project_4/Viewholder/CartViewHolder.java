package com.example.project_4.Viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder {
    TextView txt_order_name,txt_price;
    ImageView img_menu;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        //findbyId
    }
}
