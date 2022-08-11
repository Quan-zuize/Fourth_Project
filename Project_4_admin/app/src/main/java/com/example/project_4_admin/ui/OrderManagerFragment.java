package com.example.project_4_admin.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4_admin.Common.Common;
import com.example.project_4_admin.Interface.ItemClickListener;
import com.example.project_4_admin.R;
import com.example.project_4_admin.Viewholder.OrderViewHolder;
import com.example.project_4_admin.model.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Objects;

public class OrderManagerFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    MaterialSpinner spinner;
    FirebaseRecyclerAdapter<Order, OrderViewHolder> adapter;

    SharedPreferences sharedPreferences;
    static String SHARED_PREF_NAME = "myPref";
    static String KEY_SITE = "site";
    static String site_id;
    String site_address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order_manager, container, false);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");

        recyclerView = root.findViewById(R.id.list_order);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        site_id = sharedPreferences.getString(KEY_SITE, null);
        //load all Orders
        loadOrders(getSite_address());

        return root;
    }

    private void loadOrders(String site_address) {
        Query query = requests.orderByChild("site_address").equalTo(site_address);


        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(query, Order.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {
                holder.txtOrderId.setText(adapter.getRef(position).getKey());
                holder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                holder.txtSite.setText(model.getSite_address());
                holder.txtTime.setText(model.getTimeOrder().toString());

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLong) {

                    }
                });
            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_layout, parent, false);
                return new OrderViewHolder(view);
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        showUpdateDialog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(String key, Order item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getContext());
        alertDialog.setTitle("Update Order");
        alertDialog.setMessage("Please change status");

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_order_layout, null);
        spinner = view.findViewById(R.id.statusSpinner);
        spinner.setItems("Đơn hủy", "Đơn đã nhận", "Đã thanh toán");

        alertDialog.setView(view);
        final String localKey = key;

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                int status = Common.convertStatusToCode(spinner.getSelectedIndex());
                item.setStatus(status);
                requests.child(localKey).setValue(item);
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private String getSite_address() {
        DatabaseReference reference = database.getReference().child("Site");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
//                    int site_id =  Integer.parseInt(data.getKey());
                    if (Objects.equals(data.getKey(), site_id)) {
                        site_address = data.child("address").getValue(String.class);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return site_address;
    }
}