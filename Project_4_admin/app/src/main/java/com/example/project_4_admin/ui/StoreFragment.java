package com.example.project_4_admin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_4_admin.MenuListActivity;
import com.example.project_4_admin.R;
import com.example.project_4_admin.adapters.StoreHorizontalAdapter;
import com.example.project_4_admin.model.Menu;
import com.example.project_4_admin.R;
import com.example.project_4_admin.adapters.StoreHorizontalAdapter;
import com.example.project_4_admin.model.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment {
    List<Menu> menuList;
    StoreHorizontalAdapter foodAdapter;
    EditText search;
    List<Menu> filterList = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference("Menu");

    RecyclerView recyclerViewFood, recyclerViewDrink, popularFood;
    Button btn_rice, btn_hot_pot, btn_noodle_soup, btn_soup, btn_dessert, btn_fast_food, btn_combo, btn_drink;

    Menu getMenu;
    String[] category;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store, container, false);
        search = root.findViewById(R.id.search_bar);
        menuList = new ArrayList<>();

        btn_rice = root.findViewById(R.id.button_rice);
        btn_hot_pot = root.findViewById(R.id.button_hot_pot);
        btn_noodle_soup = root.findViewById(R.id.button_noodle_soup);
        btn_soup = root.findViewById(R.id.button_soup);
        btn_dessert = root.findViewById(R.id.button_dessert);
        btn_fast_food = root.findViewById(R.id.button_fast_food);
        btn_combo = root.findViewById(R.id.button_combo);
        btn_drink = root.findViewById(R.id.button_drink);

        popularFood = root.findViewById(R.id.popularFood);
        recyclerViewFood = root.findViewById(R.id.foods_recycler);

        Intent intent = new Intent(this.getContext(), MenuListActivity.class);

        btn_rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = new String[]{"Cơm"};
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });
        btn_hot_pot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = new String[]{"Lẩu"};
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });
        btn_noodle_soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = new String[]{"Mì", "Phở"};
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });
        btn_soup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = new String[]{"Súp"};
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });
        btn_dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = new String[]{"Tráng miệng"};
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });
        btn_fast_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = new String[]{"Ăn nhanh"};
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });
        btn_combo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = new String[]{"Combo"};
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });
        btn_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = new String[]{"Đồ Uống"};
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        dtf.format(now);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    int menu_id = Integer.parseInt(childSnapshot.getKey());
                    String title = childSnapshot.child("Title").getValue(String.class);
                    String category = childSnapshot.child("Category").getValue(String.class);
                    Double price = Double.valueOf(childSnapshot.child("Price").getValue(String.class));
                    String img = childSnapshot.child("Image").getValue(String.class);
                    String des = childSnapshot.child("Description").getValue(String.class);
                    getMenu = new Menu(menu_id, title, category, price, img, des);
                    menuList.add(getMenu);
                    setRecyclerViewFood();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }

            ;
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList.clear();
                if (s.toString().isEmpty()) {
                    recyclerViewFood.setAdapter(new StoreHorizontalAdapter(getActivity(), menuList));
                    foodAdapter.notifyDataSetChanged();
                } else {
                    Filter(s.toString());
                }
            }
        });

        foodAdapter = new StoreHorizontalAdapter(getContext(), menuList);;
        return root;
    }

    private void setRecyclerViewFood() {
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFood.setHasFixedSize(true);
        recyclerViewFood.setNestedScrollingEnabled(false);
        recyclerViewFood.setAdapter(foodAdapter);
    }

    private void Filter(String text) {
        for (Menu menu : menuList) {
            if (menu.getTitle().contains(text)) {
                filterList.add(menu);
            }
        }
        recyclerViewFood.setAdapter(new StoreHorizontalAdapter(getActivity(), filterList));
        foodAdapter.notifyDataSetChanged();
    }


}