package com.example.project_4_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.project_4_admin.adapters.MenuListAdapter;
import com.example.project_4_admin.adapters.StoreHorizontalAdapter;
import com.example.project_4_admin.model.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuListActivity extends AppCompatActivity {
    private ConstraintLayout mConstraintLayout;
    TextView txtCat;
    boolean isFound = false;

    Menu getMenu;
    String search = null;
    String categoryName = "";
    private List<Menu> menuList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    MenuListAdapter adapter;
    int menu_id;

    RecyclerView food_recycler;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference("Menu");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        mConstraintLayout = findViewById(R.id.constraintLayoutMenu);

        food_recycler = findViewById(R.id.food_recycler);
        txtCat = findViewById(R.id.textCat);

        adapter = new MenuListAdapter(menuList,getApplicationContext());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        food_recycler.setLayoutManager(layoutManager);
        Intent data = getIntent();
        search = data.getStringExtra("search");

        String[] category = (String[]) data.getSerializableExtra("category");
        if(category != null){
            categoryName = String.join(" ",category);
            txtCat.setText(categoryName);
            switch (categoryName){
                case "C??m":
                    mConstraintLayout.setBackgroundResource(R.drawable.bg_rice);
                    break;
                case "L???u":
                    mConstraintLayout.setBackgroundResource(R.mipmap.bg_hotpot);
                    break;
                case "M?? Ph???":
                    mConstraintLayout.setBackgroundResource(R.drawable.bg_noodles);
                    break;
                case "S??p":
                    mConstraintLayout.setBackgroundResource(R.mipmap.bg_soup);
                    break;
                case "Tr??ng mi???ng":
                    mConstraintLayout.setBackgroundResource(R.drawable.bg_dessert);
                    break;
                case "??n nhanh":
                    mConstraintLayout.setBackgroundResource(R.drawable.bg_fastfood);
                    break;
                case "????? U???ng":
                    mConstraintLayout.setBackgroundResource(R.drawable.bg_drink);
                    break;
                default:
                    break;
            }
        }else{
            txtCat.setText("");
        }

        if (category != null && search == null) {
            for (String item : category) {
                categoryName = item;
                db.orderByChild("Category").equalTo(categoryName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        menuList.clear();
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            menu_id = Integer.parseInt(childSnapshot.getKey());
                            db.child(String.valueOf(menu_id)).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String title = snapshot.child("Title").getValue(String.class);
                                    String category = snapshot.child("Category").getValue(String.class);
                                    Double price = Double.valueOf(snapshot.child("Price").getValue(String.class));
                                    String img = snapshot.child("Image").getValue(String.class);
                                    String des = snapshot.child("Description").getValue(String.class);
                                    getMenu = new Menu(menu_id, title, category, price, img, des);
                                    menuList.add(getMenu);
                                    food_recycler.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

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
        } else {
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    menuList.clear();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String title = childSnapshot.child("Title").getValue(String.class).toLowerCase();
                        if (title.contains(search)) {
                            isFound = true;
                            menu_id = Integer.parseInt(childSnapshot.getKey());
                            db.child(String.valueOf(menu_id)).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    String title = snapshot.child("Title").getValue(String.class);
                                    String category = snapshot.child("Category").getValue(String.class);
                                    Double price = Double.valueOf(snapshot.child("Price").getValue(String.class));
                                    String img = snapshot.child("Image").getValue(String.class);
                                    String des = snapshot.child("Description").getValue(String.class);
                                    getMenu = new Menu(menu_id, title, category, price, img, des);
                                    menuList.add(getMenu);
                                    food_recycler.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }if(!isFound){
                        txtCat.setText("Kh??ng t??m th???y m??n "+search);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}