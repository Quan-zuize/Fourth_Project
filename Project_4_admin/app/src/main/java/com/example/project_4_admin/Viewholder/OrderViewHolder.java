package com.example.project_4_admin.Viewholder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4_admin.Interface.ItemClickListener;
import com.example.project_4_admin.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
    public TextView txtOrderId, txtOrderStatus,txtSite, txtTime;


    private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtSite = itemView.findViewById(R.id.order_site);
        txtTime = itemView.findViewById(R.id.order_time);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//        contextMenu.setHeaderTitle("Select The Action");

        contextMenu.add(0,0,getAdapterPosition(),"Update");
//        contextMenu.add(0,1,getAdapterPosition(),"Update");
    }
}
