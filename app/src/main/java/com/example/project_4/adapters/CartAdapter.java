package com.example.project_4.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4.Interface.ChangeNumberItemsListener;
import com.example.project_4.R;
import com.example.project_4.Viewholder.CartViewHolder;
import com.example.project_4.database.Database;

import java.util.ArrayList;
import java.util.List;

import model.OrderDetail;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<OrderDetail> listData = new ArrayList<>();
    private Context context;
    private ChangeNumberItemsListener changeNumberItemsListener;

    Database database;
    double price;

    OrderDetail od;

    public CartAdapter(List<OrderDetail> listData, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listData = listData;
        this.context = context;
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    public CartAdapter(List<OrderDetail> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.viewholder_cart, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        price = listData.get(position).getPrice();
        //how to retrieve from firebase :Đ
        //holder.txt_order_name.setText(listData.get(position).getMenuId());
        holder.txt_price_item.setText(String.valueOf(listData.get(position).getPrice()));
        holder.num.setText(String.valueOf(listData.get(position).getQuantity()));

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.plusNumberFood(listData, position, new ChangeNumberItemsListener() {
                    @Override
                    public void change() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.change();
                    }
                });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.minusNumberFood(listData, position, new ChangeNumberItemsListener() {
                    @Override
                    public void change() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.change();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
