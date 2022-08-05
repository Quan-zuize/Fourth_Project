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

import com.example.project_4.DetailsActivity;
import com.example.project_4.R;

import java.util.List;

import model.Menu;

public class StoreVerticleAdapter extends RecyclerView.Adapter<StoreVerticleAdapter.ViewHolder> {
    Context context;
    List<Menu> menuList;

    public StoreVerticleAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public StoreVerticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoreVerticleAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        Bitmap bMap = BitmapFactory.decodeFile(menuList.get(position).getImage_src());
//        holder.imageView.setImageBitmap(bMap);
        holder.name.setText(menuList.get(position).getTitle());
        holder.price.setText(menuList.get(position).getPrice().intValue());

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
        ImageView foodImage;
        TextView price,name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.imageView3);
            price= itemView.findViewById(R.id.textPrice);
            name = itemView.findViewById(R.id.textName);
        }
    }
}
