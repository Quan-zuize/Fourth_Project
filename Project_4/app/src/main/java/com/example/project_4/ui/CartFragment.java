package com.example.project_4.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_4.Helper.ManagementCart;
import com.example.project_4.Interface.ChangeNumberItemsListener;
import com.example.project_4.R;
import com.example.project_4.adapters.CartAdapter;
import com.example.project_4.adapters.SpinnerAdapter;
import com.example.project_4.model.Site;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.example.project_4.model.Menu;
import com.example.project_4.model.OrderDetail;


public class CartFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference requests;

    CartAdapter adapter;
    ArrayList<Menu> menuList;

    TextView txt_empty, txt_totalFee, txt_note;
    ScrollView scrollView;
    Button btnOrder;
    Spinner spinner;
    double total ;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    int max_id ;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ManagementCart managementCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = root.findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Order");
        managementCart = new ManagementCart(this.getContext());

        //Init View
        scrollView = root.findViewById(R.id.scrollView2);
        txt_empty = root.findViewById(R.id.emptyTxt);
        txt_totalFee = root.findViewById(R.id.totalFeeTxt);
        txt_note = root.findViewById(R.id.textNote);
        spinner = root.findViewById(R.id.site_spinner);

        menuList = managementCart.getListCart();
        changeView();
        loadListFood();
        displaySite();
        calculateCart();

        Query query = requests.orderByKey().limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    try {
                        max_id = Integer.parseInt(childSnapshot.getKey());
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
                String note, status, buyer_name,site_address;
                int order_id;

                //insert to firebase

                //id last order +1
                order_id = max_id + 1;
                LocalDateTime timeOrder = LocalDateTime.now();
                status = "Đơn đã nhận";
                site_address = spinner.getSelectedItem().toString();
                note = txt_note.getText().toString();

//                requests.child(String.valueOf(order_id)).child("Buyer Name").setValue(buyer_name);
                requests.child(String.valueOf(order_id)).child("Site Address").setValue(site_address);
                requests.child(String.valueOf(order_id)).child("Total").setValue(total);
                requests.child(String.valueOf(order_id)).child("Status").setValue(status);
                requests.child(String.valueOf(order_id)).child("Note").setValue(note);
                requests.child(String.valueOf(order_id)).child("Time Order").setValue(dtf.format(timeOrder));

                for (int i = 1; i <= menuList.size(); i++) {
                    OrderDetail od = new OrderDetail();
                    od.setMenu_id(menuList.get(i).getMenu_id());
                    od.setMenu_title(menuList.get(i).getTitle());
                    od.setPrice(menuList.get(i).getPrice());
                    od.setQuantity(menuList.get(i).getNumInCart());
                    od.setTotal(menuList.get(i).getPrice() * menuList.get(i).getNumInCart());
                    requests.child(String.valueOf(order_id)).child("Detail").child(""+i).setValue(od);
                }

                //Clear cart
                new ManagementCart(getContext()).cleanCart();
                Toast.makeText(CartFragment.this.getContext(), "Thank you for ordering", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CartFragment.this.getContext(), OrderFragment.class));
            }
        });


        return root;
    }

    private void changeView(){
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
        final ArrayList<Site> list = new ArrayList<>();

        DatabaseReference reference = database.getReference().child("Site");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for(DataSnapshot data : snapshot.getChildren()){
//                    int site_id =  Integer.parseInt(data.getKey());
                    Site site = new Site(Integer.parseInt(data.getKey()),data.child("Address").getValue().toString());
                    list.add(site);
                }
                final SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(), R.layout.item_site_selected, list);
                spinner.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        deleteCart(item.getOrder());
        return super.onContextItemSelected(item);
    }

    private void deleteCart(int order) {
        managementCart.remove(order);
    }
}