package com.example.project_4.Viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    RecyclerView recyclerView;
    public TextView txt_order_name,txt_price_item, num ;
    public ImageView img_menu, plusItem, minusItem;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        recyclerView = itemView.findViewById(R.id.listCart);
        txt_order_name = itemView.findViewById(R.id.titleTxt);
        txt_price_item = itemView.findViewById(R.id.totalEachItem);
        img_menu = itemView.findViewById(R.id.picCart);
        num = itemView.findViewById(R.id.numberItemsTxt);
        minusItem = itemView.findViewById(R.id.minusCartBtn);
        plusItem = itemView.findViewById(R.id.plusCartBtn);
    }
}
