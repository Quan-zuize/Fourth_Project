package com.example.project_4.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.project_4.MenuListActivity;
import com.example.project_4.R;
import com.example.project_4.adapters.StoreHorizontalAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.project_4.model.Menu;
import com.mancj.materialsearchbar.MaterialSearchBar;

public class StoreFragment extends Fragment {
    List<Menu> recentAddList  = new ArrayList<>();
    List<Menu> popularList = new ArrayList<>();

    StoreHorizontalAdapter popularAdapter, recentAddAdapter ;

    MaterialSearchBar search;
    List<Menu> filterList = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference("Menu");

    RecyclerView popularFood, recentAddFood;
    Button btn_rice, btn_hot_pot, btn_noodle_soup, btn_soup, btn_dessert, btn_fast_food, btn_combo, btn_drink;

    Menu getRecentAddMenu;
    Menu getPopularMenu;
    String[] category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store, container, false);
        search = root.findViewById(R.id.search_bar);

        btn_rice = root.findViewById(R.id.button_rice);
        btn_hot_pot = root.findViewById(R.id.button_hot_pot);
        btn_noodle_soup = root.findViewById(R.id.button_noodle_soup);
        btn_soup = root.findViewById(R.id.button_soup);
        btn_dessert = root.findViewById(R.id.button_dessert);
        btn_fast_food = root.findViewById(R.id.button_fast_food);
        btn_combo = root.findViewById(R.id.button_combo);
        btn_drink = root.findViewById(R.id.button_drink);

        recentAddFood = root.findViewById(R.id.recentAddFood);
        popularFood = root.findViewById(R.id.foods_recycler);

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

        search.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                String search_text = search.getText().toLowerCase();
                intent.putExtra("search", search_text);
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        ArrayList<String> categoryList = new ArrayList<>();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String category = childSnapshot.child("Category").getValue(String.class);
                    categoryList.add(category);
                }
                ArrayList<String> categoryList2 = (ArrayList<String>) categoryList.stream().distinct().collect(Collectors.toList());
                for (String item : categoryList2) {
                    Query getPopular = db.orderByChild("Category").equalTo(item).limitToFirst(1);
                    getPopular.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                //menu_id = ;
                                popularList.clear();
                                db.child(childSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int menu_id = Integer.parseInt(childSnapshot.getKey());
                                        String title = childSnapshot.child("Title").getValue(String.class);
                                        String category = childSnapshot.child("Category").getValue(String.class);
                                        Double price = Double.valueOf(childSnapshot.child("Price").getValue(String.class));
                                        String img = childSnapshot.child("Image").getValue(String.class);
                                        String des = childSnapshot.child("Description").getValue(String.class);
                                        getPopularMenu = new Menu(menu_id, title, category, price, img, des);
                                        popularList.add(getPopularMenu);
                                        setPopularRecyclerViewFood();
                                        popularAdapter.notifyDataSetChanged();
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query getRecentAdd = db.orderByChild("Date Add").limitToLast(5);
        getRecentAdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    recentAddList.clear();
                    db.child(childSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int menu_id = Integer.parseInt(childSnapshot.getKey());
                            String title = childSnapshot.child("Title").getValue(String.class);
                            String category = childSnapshot.child("Category").getValue(String.class);
                            Double price = Double.valueOf(childSnapshot.child("Price").getValue(String.class));
                            String img = childSnapshot.child("Image").getValue(String.class);
                            String des = childSnapshot.child("Description").getValue(String.class);
                            getRecentAddMenu  = new Menu(menu_id, title, category, price, img, des);                            recentAddList.add(getRecentAddMenu);
                            setRecentRecycleViewFood();
                            recentAddAdapter.notifyDataSetChanged();
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

//        search.addTextChangeListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                filterList.clear();
//                if (s.toString().isEmpty()) {
//                    popularFood.setAdapter(new StoreHorizontalAdapter(getActivity(), popularList));
//                    popularAdapter.notifyDataSetChanged();
//                } else {
//                    Filter(s.toString());
//                }
//            }
//        });

        popularAdapter = new StoreHorizontalAdapter(getContext(), popularList);
        recentAddAdapter = new StoreHorizontalAdapter(getContext(), recentAddList);
        return root;
    }

    private void setPopularRecyclerViewFood() {

        popularFood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularFood.setHasFixedSize(true);
        popularFood.setNestedScrollingEnabled(false);
        popularFood.setAdapter(popularAdapter);
    }

    private void setRecentRecycleViewFood(){
        recentAddFood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recentAddFood.setHasFixedSize(true);
        recentAddFood.setNestedScrollingEnabled(false);
        recentAddFood.setAdapter(recentAddAdapter);
    }

    private void Filter(String text) {
        for (Menu menu : popularList) {
            if (menu.getTitle().contains(text)) {
                filterList.add(menu);
            }
        }
        popularFood.setAdapter(new StoreHorizontalAdapter(getActivity(), filterList));
        popularAdapter.notifyDataSetChanged();
    }


}