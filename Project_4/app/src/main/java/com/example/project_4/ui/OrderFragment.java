package com.example.project_4.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project_4.LoginActivity;
import com.example.project_4.R;
import com.example.project_4.Remote.APIService;
import com.example.project_4.Store_dashboardActivity;
import com.example.project_4.Viewholder.OrderViewHolder;

import com.example.project_4.adapters.OrderAdapter;
import com.example.project_4.model.DataMessage;
import com.example.project_4.model.Menu;
import com.example.project_4.model.MyResponse;
import com.example.project_4.model.OrderDetail;
import com.example.project_4.model.Token;
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
import com.google.gson.Gson;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;
    FirebaseUser user;

    FirebaseRecyclerAdapter<Order, OrderViewHolder> adapter;

    List<Order> orderList  = new ArrayList<>();
    List<OrderDetail> listDetails = new ArrayList<>();
    List<String> keys = new ArrayList<>();
    OrderAdapter orderAdapter;

    int order_id, status;
    String buyer_name, buyer_phone, buyer_id;
    String site_address, note, timeOrder, timeTaken;
    Double total;

    APIService mService;
    String shop_manager_id;

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
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        user = FirebaseAuth.getInstance().getCurrentUser();
        orderAdapter = new OrderAdapter(orderList,getContext());

        loadOrders(user.getUid());
        return root;
    }

//    private void loadOrders(String userId) {
//        Query query = requests.orderByChild("buyerId").equalTo(userId);
//        FirebaseRecyclerOptions<Order> options =
//                new FirebaseRecyclerOptions.Builder<Order>()
//                        .setQuery(query, Order.class)
//                        .build();
//
//        adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {
//            @NonNull
//            @Override
//            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                // Create a new instance of the ViewHolder, in this case we are using a custom
//                // layout called R.layout.order_layout for each item
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.order_layout, parent, false);
//                return new OrderViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, Order model) {
//                holder.txtOrderId.setText("#" + adapter.getRef(position).getKey());
//                holder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
//                holder.txtSite.setText(model.getSiteAddress());
//                //holder.txtTime.setText(dtf.format());
//            }
//        };
//    }

    private void loadOrders(String userId) {
        //SELECT * FROM ORDER WHERE buyerId = userId
        Query query = requests.orderByChild("buyerId").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    orderList.clear();
                    requests.child(childSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            keys.add(snapshot.getKey());
                            order_id = Integer.parseInt(snapshot.child("orderID").getValue().toString());
                            buyer_id = snapshot.child("buyerId").getValue(String.class);
                            buyer_name = snapshot.child("buyerName").getValue(String.class);
                            buyer_phone = snapshot.child("buyerPhone").getValue(String.class);
                            status = snapshot.child("status").getValue(Integer.class);
                            total = Double.valueOf(snapshot.child("total").getValue().toString());
                            site_address = snapshot.child("siteAddress").getValue(String.class);
                            note = snapshot.child("note").getValue(String.class);
                            timeOrder = snapshot.child("timeOrder").getValue(String.class);
                            timeTaken = snapshot.child("timeTaken").getValue(String.class);

                            for(DataSnapshot data : snapshot.child("details").getChildren()){
                                OrderDetail orderDetail = data.getValue(OrderDetail.class);
                                listDetails.add(orderDetail);
                            }
                            Order order = new Order(order_id, status, buyer_id, buyer_name, buyer_phone, total, site_address, note, timeOrder, timeTaken, listDetails);
                            orderList.add(0,order);
                            orderAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(orderAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(orderList.get(item.getOrder()).getStatus() != 0){
            orderList.get(item.getOrder()).setStatus(3);
//            showCancelDialog(keys.get(keys.size() - 1 - item.getOrder()), orderList.get(item.getOrder()));
        }else{
            Toast.makeText(getContext(),"Đơn không hủy được.", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

//    private void showCancelDialog(String key, Order item) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage("Bạn có muốn hủy đơn không?")
//                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        final String localKey = key;
//                        item.setStatus(0);
//                        requests.child(localKey).setValue(item);
//                        sendOrderStatusToManager(item);
//                    }
//                })
//                .setNegativeButton("Không",null);
//    }
//
//    private void sendOrderStatusToManager(Order item) {
//        DatabaseReference tokens = database.getReference("Token");
//        Query data = tokens.orderByChild("serverToken").equalTo(true);
//        data.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot postSnapShot : snapshot.getChildren()){
//                    if(Objects.equals(postSnapShot.getKey(), shop_manager_id)){
//                        Token serverToken = postSnapShot.getValue(Token.class);
//
//                        //Create raw value to send
////                        Notification notification = new Notification("Anya Shop","Bạn có đơn đặt mới #"+item.getOrderID());
////                        Sender content = new Sender(serverToken.getToken(),notification);
//                        Map<String,String> dataSend = new HashMap<>();
//                        dataSend.put("title","Anya Shop");
//                        dataSend.put("message","Bạn có đơn đặt mới #"+item.getOrderID());
//                        DataMessage dataMessage = new DataMessage(serverToken.getToken(),dataSend);
//
//                        String test = new Gson().toJson(dataMessage);
//
//                        mService.sendNotification(dataMessage).enqueue(new Callback<MyResponse>() {
//                            @Override
//                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                                //Only run when get result
//                                if(response.code() == 200){
//                                    if(response.body().success == 1){
//                                        Toast.makeText(getContext(), "Cảm ơn bạn rất nhiều ^^", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(OrderFragment.this.getContext(), Store_dashboardActivity.class));
//                                    }else{
//                                        Toast.makeText(getContext(),"Lỗi đặt hàng!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<MyResponse> call, Throwable t) {
//                                Log.e("ERROR",t.getMessage());
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}