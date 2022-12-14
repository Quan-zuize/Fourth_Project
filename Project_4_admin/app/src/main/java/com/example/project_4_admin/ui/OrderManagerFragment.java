package com.example.project_4_admin.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4_admin.Common.Common;
import com.example.project_4_admin.R;
import com.example.project_4_admin.Remote.APIService;
import com.example.project_4_admin.adapters.OrderAdapter;
import com.example.project_4_admin.model.DataMessage;
import com.example.project_4_admin.model.MyResponse;
import com.example.project_4_admin.model.Order;
import com.example.project_4_admin.model.OrderDetail;
import com.example.project_4_admin.model.Token;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderManagerFragment extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    MaterialSpinner spinner;
    //FirebaseRecyclerAdapter<Order, OrderViewHolder> adapter;
    OrderAdapter orderAdapter;

    SharedPreferences sharedPreferences;
    static String SHARED_PREF_NAME = "myPref";
    static String KEY_ADDRESS = "address";
    static String site_address;

    List<Order> orderList = new ArrayList<>();
    List<OrderDetail> listDetails = new ArrayList<>();
    int order_id;
    String buyer_id, buyer_name, buyer_phone;
    String note, timeOrder, timeTaken;
    Double total;
    int status;

    List<String> keys = new ArrayList<>();

    APIService mService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order_manager, container, false);

        //Init service
        mService = Common.getFCMService();

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");

        recyclerView = root.findViewById(R.id.list_order);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        site_address = sharedPreferences.getString(KEY_ADDRESS, null);
        orderAdapter = new OrderAdapter(orderList, getContext());
        //load all Orders
        //getSite_address(site_id);
        loadOrders(site_address);
        return root;
    }

    private void loadOrders(String address) {
        //SELECT * FROM ORDER WHERE site_address = address
        Query query = requests.orderByChild("siteAddress").equalTo(address);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    orderList.clear();
                    requests.child(childSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            keys.add(snapshot.getKey());
                            listDetails.clear();
                            order_id = Integer.parseInt(snapshot.child("orderID").getValue().toString());
                            buyer_id = snapshot.child("buyerId").getValue(String.class);
                            buyer_name = snapshot.child("buyerName").getValue(String.class);
                            buyer_phone = snapshot.child("buyerPhone").getValue(String.class);
                            status = snapshot.child("status").getValue(Integer.class);
                            total = Double.valueOf(snapshot.child("total").getValue().toString());
                            //site_address = snapshot.child("siteAddress").getValue(String.class);
                            note = snapshot.child("note").getValue(String.class);
                            timeOrder = snapshot.child("timeOrder").getValue(String.class);
                            timeTaken = snapshot.child("timeTaken").getValue(String.class);

                            for (DataSnapshot data : snapshot.child("details").getChildren()) {
                                OrderDetail orderDetail = data.getValue(OrderDetail.class);
                                listDetails.add(orderDetail);
                            }
                            Order order = new Order(order_id, status, buyer_id, buyer_name, buyer_phone, total, address, note, timeOrder, timeTaken, listDetails);
                            orderList.add(0, order);
                            recyclerView.setAdapter(orderAdapter);
                            orderAdapter.notifyDataSetChanged();
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
        if(orderList.get(item.getOrder()).getStatus() == 1 || orderList.get(item.getOrder()).getStatus() == 2){
            showUpdateDialog(keys.get(keys.size() - 1 - item.getOrder()), orderList.get(item.getOrder()));
        }else{
            Toast.makeText(getContext(),"????n ???? c???p nh???t.", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(String key, Order item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getContext());
        alertDialog.setTitle("C???p nh???t ????n h??ng");
        alertDialog.setMessage("?????i tr???ng th??i ????n");

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_order_layout, null);
        spinner = view.findViewById(R.id.statusSpinner);
        spinner.setItems("????n h???y", "????n ???? nh???n", "???? thanh to??n");

        alertDialog.setView(view);
        final String localKey = key;

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                status = Common.convertStatusToCode(spinner.getSelectedIndex());
                if(status == 3){
                    item.setTimeTaken(Common.getDate());
                }
                item.setStatus(status);
                requests.child(localKey).setValue(item);
                sendOrderStatusToBuyer(item);
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

    private void sendOrderStatusToBuyer(Order item) {
        DatabaseReference tokens = database.getReference("Token");
        tokens.orderByKey().equalTo(item.getBuyerId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                            Token token = postSnapShot.getValue(Token.class);

                            //Make raw payload
//                            Notification notification = new Notification("Anya Shop", "????n c???a b???n #" + item.getOrderID() + " ???? c???p nh???t : "+spinner.getText().toString());
//                            Sender content = new Sender(token.getToken(), notification);
                            Map<String,String> dataSend = new HashMap<>();
                            dataSend.put("title","Anya Shop");
                            dataSend.put("message","????n c???a b???n #" + item.getOrderID() + " ???? c???p nh???t : "+spinner.getText().toString());
                            DataMessage dataMessage = new DataMessage(token.getToken(),dataSend);

                            mService.sendNotification(dataMessage).enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.body().success == 1) {
                                        Toast.makeText(getContext(), "C???p nh???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                                        //startActivity(new Intent(CartFragment.this.getContext(), Store_dashboardActivity.class));
                                    } else {
                                        Toast.makeText(getContext(), "????n ?????t ???? c???p nh???t nh??ng th??ng b??o ch??a ???????c g???i t???i", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {
                                    Log.e("ERROR", t.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}