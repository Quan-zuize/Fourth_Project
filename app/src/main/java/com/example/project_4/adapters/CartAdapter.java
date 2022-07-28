package com.example.project_4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4.R;
import com.example.project_4.Viewholder.CartViewHolder;

import java.util.ArrayList;
import java.util.List;

import model.OrderDetail;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private List<OrderDetail> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<OrderDetail> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.fragment_cart,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        double price = listData.get(position).getPrice();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
