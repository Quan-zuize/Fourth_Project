package com.example.project_4_admin.Viewholder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4_admin.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener{
    public TextView txtOrderId, txtOrderStatus,txtPhone, txtTime;


    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtPhone = itemView.findViewById(R.id.order_phone);
        txtTime = itemView.findViewById(R.id.order_time);

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }


    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select Action");
        contextMenu.add(0,0,getAdapterPosition(),"UPDATE");
    }
}
