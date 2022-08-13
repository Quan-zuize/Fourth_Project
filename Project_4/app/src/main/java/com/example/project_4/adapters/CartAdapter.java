package com.example.project_4.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_4.Helper.ManagementCart;
import com.example.project_4.Interface.ChangeNumberItemsListener;
import com.example.project_4.R;
import com.example.project_4.Store_dashboardActivity;
import com.example.project_4.Viewholder.CartViewHolder;

import java.util.ArrayList;

import com.example.project_4.model.Menu;
import com.example.project_4.ui.CartFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    ArrayList<Menu> listData;
    ManagementCart managementCart;
    ChangeNumberItemsListener changeNumberItemsListener;
    private Context context;

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    double price;

    public CartAdapter(ArrayList<Menu> listData, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listData = listData;
        this.managementCart = new ManagementCart(context,firebaseUser.getUid());
        this.changeNumberItemsListener = changeNumberItemsListener;
        this.context = context;
    }

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
        //how to retrieve from firebase
        Glide.with(holder.itemView.getContext()).load(listData.get(position).getImage_src()).into(holder.img_menu);
        holder.txt_order_name.setText(listData.get(position).getTitle());
        holder.txt_price_item.setText(String.format("%,.0f", price*listData.get(position).getNumInCart()));
        holder.num.setText(String.valueOf(listData.get(position).getNumInCart()));

        holder.plusItem.setOnClickListener((View view) -> {
            managementCart.plusNumberFood(listData, position, () ->{
                notifyDataSetChanged();
                changeNumberItemsListener.change();
                if(((Store_dashboardActivity) this.context) instanceof Store_dashboardActivity) {
                    ((Store_dashboardActivity)((Store_dashboardActivity) this.context))
                            .reloadFragment();
                }
            });
        });

        holder.minusItem.setOnClickListener((View view)->{
            managementCart.minusNumberFood(listData, position, () ->{
                notifyDataSetChanged();
                changeNumberItemsListener.change();
                if(((Store_dashboardActivity) this.context) instanceof Store_dashboardActivity) {
                    ((Store_dashboardActivity)((Store_dashboardActivity) this.context))
                            .reloadFragment();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
