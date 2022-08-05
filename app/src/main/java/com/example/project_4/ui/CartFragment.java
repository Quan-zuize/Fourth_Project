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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.project_4.Helper.ManagementCart;
import com.example.project_4.Interface.ChangeNumberItemsListener;
import com.example.project_4.R;
import com.example.project_4.adapters.CartAdapter;
import com.example.project_4.adapters.SpinnerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.Order;
import model.OrderDetail;
import model.Site;


public class CartFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference requests;

    CartAdapter adapter;

    TextView txt_empty, txt_totalFee, txt_note;
    ScrollView scrollView;
    Button btnOrder;
    Spinner spinner;
    double total ;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ManagementCart managementCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");
        managementCart = new ManagementCart(this.getContext());

        //Init View
        scrollView = root.findViewById(R.id.scrollView2);
        txt_empty = root.findViewById(R.id.emptyTxt);
        txt_totalFee = root.findViewById(R.id.totalFeeTxt);
        txt_note = root.findViewById(R.id.textNote);
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
                //insert to firebase

                ArrayList<OrderDetail> list = new ArrayList<>();
                String note, status, buyer_name,site_address;

                LocalDateTime timeOrder = LocalDateTime.now();
                status = "In Process";
                site_address = spinner.getSelectedItem().toString();
                note = txt_note.getText().toString();

                //id last order +1
                //requests.child(String.valueOf())

                //databaseReference.child("Users").child(phone).setValue(userObject);
                //Clear cart
                //new ManagementCart(getContext()).cleanCart();

                //finish();
            }
        });


        return root;
    }

    private void loadListFood() {
        linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CartAdapter(managementCart.getListCart(), this.getContext(), new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calculateCart();
            }
        });

        recyclerView.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            txt_empty.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            txt_empty.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calculateCart() {
        //Calculate total price
        total = managementCart.getTotal();
        txt_totalFee.setText(total + " vnd");
    }

    private void displaySite() {
        final ArrayList<String> list = new ArrayList<>();

        DatabaseReference reference = database.getReference().child("Site");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for(DataSnapshot data : snapshot.getChildren()){
//                    int site_id =  Integer.parseInt(data.getKey());
                    list.add(data.child("address").getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this.getContext(),R.layout.item_site, list);
        spinner.setAdapter(spinnerAdapter);
    }
}