package com.example.project_4.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_4.Common.Common;
import com.example.project_4.Helper.ManagementCart;
import com.example.project_4.Interface.ChangeNumberItemsListener;
import com.example.project_4.R;
import com.example.project_4.Remote.APIService;
import com.example.project_4.Store_dashboardActivity;
import com.example.project_4.adapters.CartAdapter;
import com.example.project_4.model.DataMessage;
import com.example.project_4.model.MyResponse;
import com.example.project_4.model.Order;
import com.example.project_4.model.Token;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.example.project_4.model.Menu;
import com.example.project_4.model.OrderDetail;
import com.google.gson.Gson;

import org.angmarch.views.NiceSpinner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference requests;

    CartAdapter adapter;
    ArrayList<Menu> menuList;

    TextView txt_empty, txt_totalFee, txt_note;
    ScrollView scrollView;
    Button btnOrder;
    NiceSpinner spinner;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    int max_id ;
    int order_id, site_id = 0;
    String buyer_id;
    String note, buyer_name,buyer_phone, site_address;
    double total ;
    LocalDateTime timeOrder = LocalDateTime.now();
    List<OrderDetail> listDetails = new ArrayList<>();
    Order order;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ManagementCart managementCart;

    SharedPreferences sharedPreferences;
    private static String SHARED_PREF_NAME = "myPref";
    private static String KEY_ID = "id";
    private static String KEY_NAME = "name";
    private static String KEY_PHONE = "phone";

    APIService mService;
    String shop_manager_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        //Init service
        mService = Common.getFCMService();

        recyclerView = root.findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        buyer_id = sharedPreferences.getString(KEY_ID, null);
        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");
        managementCart = new ManagementCart(this.getContext(),buyer_id);

        //Init View
        scrollView = root.findViewById(R.id.scrollView2);
        txt_empty = root.findViewById(R.id.emptyTxt);
        txt_totalFee = root.findViewById(R.id.totalFeeTxt);
        txt_note = root.findViewById(R.id.textNote);
        spinner = root.findViewById(R.id.site_spinner);

        menuList = managementCart.getListCart();
        reloadView();
        loadListFood();
        displaySite();
        calculateCart();

        Query query = requests.orderByChild("orderID");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    try {
                        max_id = childSnapshot.child("orderID").getValue(Integer.class);
                    } catch (NumberFormatException ex) { // handle your exception

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnOrder = root.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insert to firebase
                //id last order +1
                order_id = max_id + 1;
                buyer_name = sharedPreferences.getString(KEY_NAME, null);
                buyer_phone = sharedPreferences.getString(KEY_PHONE,null);
                site_address = spinner.getSelectedItem().toString();
                site_id = spinner.getSelectedIndex()+1;
                getShopManagerId(site_id);
                note = txt_note.getText().toString();

                for (int i = 0; i < menuList.size(); i++) {
                    OrderDetail od = new OrderDetail();
                    od.setMenuId(menuList.get(i).getMenu_id());
                    od.setMenuTitle(menuList.get(i).getTitle());
                    od.setPrice(menuList.get(i).getPrice());
                    od.setQuantity(menuList.get(i).getNumInCart());
                    od.setTotal(menuList.get(i).getPrice() * menuList.get(i).getNumInCart());
                    listDetails.add(od);
//                    requests.child(String.valueOf(order_id)).child("Detail").child(""+i).setValue(od);
                }

                order = new Order(order_id,buyer_id,buyer_name,buyer_phone,total,site_address,note, dtf.format(timeOrder),listDetails);

//                requests.child(String.valueOf(order_id)).child("Buyer ID").setValue(buyer_id);
//                requests.child(String.valueOf(order_id)).child("Buyer Name").setValue(buyer_name);
//                requests.child(String.valueOf(order_id)).child("Site Address").setValue(site_address);
//                requests.child(String.valueOf(order_id)).child("Total").setValue(total);
//                requests.child(String.valueOf(order_id)).child("Status").setValue(status);
//                requests.child(String.valueOf(order_id)).child("Note").setValue(note);
//                requests.child(String.valueOf(order_id)).child("Time Order").setValue(dtf.format(timeOrder));

                String order_number = String.valueOf(System.currentTimeMillis());
                requests.child(order_number).setValue(order);

                //Clear cart
                managementCart.cleanCart();
            }
        });


        return root;
    }

    private void getShopManagerId(int id){
        database.getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    if(childSnapshot.child("site_id").getValue(Integer.class) == id){
                        shop_manager_id = childSnapshot.getKey();
                        //send notification
                        sendNotificationOrder(order);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotificationOrder(Order item) {
        DatabaseReference tokens = database.getReference("Token");
        Query data = tokens.orderByChild("serverToken").equalTo(true);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapShot : snapshot.getChildren()){
                    if(Objects.equals(postSnapShot.getKey(), shop_manager_id)){
                        Token serverToken = postSnapShot.getValue(Token.class);

                        //Create raw value to send
//                        Notification notification = new Notification("Anya Shop","Bạn có đơn đặt mới #"+item.getOrderID());
//                        Sender content = new Sender(serverToken.getToken(),notification);
                        Map<String,String> dataSend = new HashMap<>();
                        dataSend.put("title","Anya Shop");
                        dataSend.put("message","Bạn có đơn đặt mới #"+item.getOrderID());
                        DataMessage dataMessage = new DataMessage(serverToken.getToken(),dataSend);

                        String test = new Gson().toJson(dataMessage);

                        mService.sendNotification(dataMessage).enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                //Only run when get result
                                if(response.code() == 200){
                                    if(response.body().success == 1){
                                        Toast.makeText(getContext(), "Cảm ơn bạn rất nhiều ^^", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(CartFragment.this.getContext(), Store_dashboardActivity.class));
                                    }else{
                                        Toast.makeText(getContext(),"Lỗi đặt hàng!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {
                                Log.e("ERROR",t.getMessage());
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void reloadView(){
        if (menuList.isEmpty()) {
            txt_empty.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            txt_empty.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void loadListFood() {
        linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CartAdapter(menuList, this.getContext(), new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calculateCart();
            }
        });
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void calculateCart() {
        //Calculate total price
        total = managementCart.getTotal();
        txt_totalFee.setText(String.format("%,.0f", total) + " đ");
    }

    private void displaySite() {
        List<String> dataset = new LinkedList<>();

        DatabaseReference reference = database.getReference().child("Site");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    dataset.add(data.child("Address").getValue().toString());
                }
                spinner.attachDataSource(dataset);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        menuList.remove(item.getOrder());
        managementCart.cleanCart();
        for(Menu menu : menuList){
            managementCart.insertFood(menu);
        }
        //Refresh
        loadListFood();
        adapter.notifyDataSetChanged();
        if(((Store_dashboardActivity) this.getContext()) instanceof Store_dashboardActivity) {
            ((Store_dashboardActivity)((Store_dashboardActivity) this.getContext()))
                    .reloadFragment();
        }
        return true;
    }
}