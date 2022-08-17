package com.example.project_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.project_4.adapters.MenuListAdapter;
import com.example.project_4.adapters.StoreHorizontalAdapter;
import com.example.project_4.model.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuListActivity extends AppCompatActivity {
    TextView txtCat;

    Menu getMenu;
    String categoryName = "";
    private List<Menu> menuList = new ArrayList<>();

    MenuListAdapter adapter;
    int menu_id;
//    ArrayList<String> imgList = new ArrayList<>();
    RecyclerView food_recycler;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference("Menu");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        food_recycler = findViewById(R.id.food_recycler);
        txtCat = findViewById(R.id.textCat);

        adapter = new MenuListAdapter(menuList,getApplicationContext());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        food_recycler.setLayoutManager(layoutManager);
        Intent data = getIntent();

        String[] category = (String[]) data.getSerializableExtra("category");
        categoryName = String.join(" ",category);

        txtCat.setText(categoryName);
        if (category == null) {
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    menuList.clear();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        menu_id = Integer.parseInt(childSnapshot.getKey());
                        db.child(String.valueOf(menu_id)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String title = snapshot.child("Title").getValue(String.class);
                                String category = snapshot.child("Category").getValue(String.class);
                                Double price = Double.valueOf(snapshot.child("Price").getValue(String.class));
                                String img = snapshot.child("Image").getValue(String.class);
                                String des = snapshot.child("Description").getValue(String.class);
                                getMenu = new Menu(menu_id, title, category, price, img, des);                                menuList.add(getMenu);
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
        } else {
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
        }
    }
}