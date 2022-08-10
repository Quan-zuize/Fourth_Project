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
import com.example.project_4.model.Menu;

import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder>{
    private List<Menu> menuList;
    private Context context;

    public MenuListAdapter(List<Menu> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.food_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Menu menu = menuList.get(position);
        Glide.with(holder.itemView.getContext()).load(menu.getImage_src()).into(holder.mImage);
        holder.mFoodName.setText(menu.getTitle());
        holder.mFoodPrice.setText(String.format("%,.0f", menu.getPrice()).concat(" đ"));

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

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImage;
        public TextView mFoodName;
        public TextView mFoodPrice;
        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            mImage = itemView.findViewById(R.id.mImage);
            mFoodName = itemView.findViewById(R.id.mFoodName);
            mFoodPrice = itemView.findViewById(R.id.mFoodPrice);
        }
    }
}
