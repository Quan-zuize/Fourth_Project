package com.example.project_4.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.project_4.Interface.ChangeNumberItemsListener;
import com.example.project_4.R;
import com.example.project_4.adapters.CartAdapter;
import com.example.project_4.database.Database;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.OrderDetail;

public class CartFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference requests;

    List<OrderDetail> cart = new ArrayList<>();
    CartAdapter adapter;

    TextView txt_empty, txt_totalFee;
    ScrollView scrollView;
    Button btnOrder;
    Spinner spinner;
    float total = 0;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Database managementCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");
        managementCart = new Database(this.getContext());

        //Init View
        scrollView = root.findViewById(R.id.scrollView2);
        txt_empty = root.findViewById(R.id.emptyTxt);
        txt_totalFee = root.findViewById(R.id.totalFeeTxt);
        spinner = root.findViewById(R.id.site);

        recyclerView = root.findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);

        loadListFood();
        displaySite();
        calculateCart();

        btnOrder = root.findViewById(R.id.btnOrder);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                //get buyer name by firebase
                //total
                //status = 0
                //site_id
                //Time_Order = now

                //id last order +1
                //requests.child(String.valueOf())

                //Clear cart
                //new Database(getContext()).cleanCart();
                //finish();
            }
        });


        return root;
    }

    private void loadListFood() {
        linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        if (managementCart.getCart().isEmpty()) {
            txt_empty.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            txt_empty.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            cart = managementCart.getCart();
        }

        adapter = new CartAdapter(cart, this.getContext(), new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calculateCart();
            }
        });
    }

    private void calculateCart() {
        //Calculate total price
        for (OrderDetail dt : cart) {
            total += (dt.getPrice() * dt.getQuantity());
        }
        txt_totalFee.setText(total + " vnd");
    }

    private void displaySite() {
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter(getContext(),R.layout.item_site, list);
        spinner.setAdapter(adapter);

        DatabaseReference reference = database.getReference().child("Site");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    list.add(data.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}