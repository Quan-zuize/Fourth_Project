package com.example.project_4.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4.Helper.ManagementCart;
import com.example.project_4.Interface.ChangeNumberItemsListener;
import com.example.project_4.R;
import com.example.project_4.Viewholder.CartViewHolder;
import com.example.project_4.database.Database;

import java.util.ArrayList;
import java.util.List;

import model.Menu;
import model.OrderDetail;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    ArrayList<Menu> listData;
    ManagementCart managementCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    double price;

    OrderDetail od;

    public CartAdapter(ArrayList<Menu> listData, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listData = listData;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

//    public CartAdapter(List<Menu> listData, Context context) {
//        this.listData = listData;
//        this.context = context;
//    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.viewholder_cart, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        price = listData.get(position).getPrice();
        //how to retrieve from firebase :Đ
        holder.txt_order_name.setText(listData.get(position).getTitle());
        holder.txt_price_item.setText(String.valueOf(price*listData.get(position).getNumInCart()));
        holder.num.setText(String.valueOf(listData.get(position).getNumInCart()));

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.plusNumberFood(listData, position, new ChangeNumberItemsListener() {
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
                managementCart.minusNumberFood(listData, position, new ChangeNumberItemsListener() {
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
