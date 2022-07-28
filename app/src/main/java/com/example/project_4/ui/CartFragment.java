package com.example.project_4.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_4.R;
import com.example.project_4.adapters.CartAdapter;
import com.example.project_4.database.Database;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.OrderDetail;

public class CartFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference requests;

    List<OrderDetail> cart = new ArrayList<>();
    CartAdapter adapter;

    TextView txtTotalPrice;
    Button btnOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Firebase
//        database = FirebaseDatabase.getInstance();
//        requests = database.getReference("Menu");

        //Init
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                //get buyer name by shared preferences
                //total
                //status = 0
                //site_id
                //Time_Order = now

                //id last order +1
                //requests.child(String.valueOf())

                //Clear cart
                new Database(getContext()).cleanCart();
                //finish();
            }
        });

        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    private void loadListFood(){
        cart = new Database(this.getContext()).getCart();
        adapter= new CartAdapter(cart,this.getContext());
//        recylcerView.setAdapter(adapter);

        //Calculate total price
        float total = 0;
        for(OrderDetail dt : cart){
            total += (dt.getPrice() * dt.getQuantity());
        }

        //txtTotal.setText(String.valueOf(total)+" vnd");
    }
}