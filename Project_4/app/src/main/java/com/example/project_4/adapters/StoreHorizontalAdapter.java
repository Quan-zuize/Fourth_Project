package com.example.project_4.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_4.DetailsActivity;
import com.example.project_4.R;

import java.util.List;

import com.example.project_4.model.Menu;

public class StoreHorizontalAdapter extends RecyclerView.Adapter<StoreHorizontalAdapter.ViewHolder> {
    Context context;
    List<Menu> menuList;

    public StoreHorizontalAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public StoreHorizontalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoreHorizontalAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView.getContext()).load(menuList.get(position).getImage_src()).into(holder.mImage);
;
        holder.name.setText(menuList.get(position).getTitle());
        holder.price.setText(String.format("%,.0f", menuList.get(position).getPrice()).concat(" đ"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                intent.putExtra("object",menuList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView price,name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.mImage);
            price= itemView.findViewById(R.id.textPrice);
            name = itemView.findViewById(R.id.textName);
        }
    }
}
