package com.example.project_4_admin.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_4_admin.MenuAddActivity;
import com.example.project_4_admin.R;
import com.example.project_4_admin.adapters.MenuAdapter;
import com.example.project_4_admin.adapters.MenuListAdapter;
import com.example.project_4_admin.model.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {
    TextView menu_category;
    Menu getMenu;
    private List<Menu> menuList = new ArrayList<>();
    MenuAdapter adapter;
    int menu_id;
    RecyclerView menu_recycler;
    ImageView addActivity;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference("Menu");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        addActivity = root.findViewById(R.id.addActivity);
        menu_category = root.findViewById(R.id.menu_category);
        menu_recycler = root.findViewById(R.id.menu_recycler);

        adapter = new MenuAdapter(menuList, getContext());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        menu_recycler.setLayoutManager(layoutManager);

        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MenuAddActivity.class);
                startActivity(intent);

            }
        });

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    menu_id = Integer.parseInt(childSnapshot.getKey());
                    menuList.clear();
                    db.child(String.valueOf(menu_id)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String title = snapshot.child("Title").getValue(String.class);
                            String category = snapshot.child("Category").getValue(String.class);
                            Double price = Double.valueOf(snapshot.child("Price").getValue(String.class));
                            String img = snapshot.child("Image").getValue(String.class);
                            String des = snapshot.child("Description").getValue(String.class);
                            getMenu = new Menu(Integer.parseInt(snapshot.getKey()), title, category, price, img, des);
                            menuList.add(getMenu);
                            menu_recycler.setAdapter(adapter);
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
        return root;
    }
    public void getMenuFromDB(){

    }
}