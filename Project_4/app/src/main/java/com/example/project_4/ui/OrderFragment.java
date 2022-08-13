package com.example.project_4.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_4.Common.Common;
import com.example.project_4.R;
import com.example.project_4.Viewholder.OrderViewHolder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.example.project_4.model.Order;
import com.google.firebase.database.ValueEventListener;

import java.time.format.DateTimeFormatter;

public class OrderFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Order,OrderViewHolder> adapter;

    FirebaseUser user;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");

        recyclerView = root.findViewById(R.id.list_order);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        user = FirebaseAuth.getInstance().getCurrentUser();

        loadOrders(user.getUid());
        return root;
    }

    private void loadOrders(String id) {
        Query query = requests.orderByChild("Buyer ID").equalTo(id);
        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(query, Order.class)
                        .build();


        adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {
            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.order_layout for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_layout, parent, false);
                return new OrderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, Order model) {
                holder.txtOrderId.setText("#" + adapter.getRef(position).getKey());
                holder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                holder.txtSite.setText(model.getSite_address());
                //holder.txtTime.setText(dtf.format());
            }
        };
        //options.getSnapshots()
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}