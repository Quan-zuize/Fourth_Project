package com.example.project_4.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.project_4.R;
import com.example.project_4.adapters.StoreVerticleAdapter;

import java.util.ArrayList;
import java.util.List;

import model.Menu;

public class StoreFragment extends Fragment {
    List<Menu> menuList;
    StoreVerticleAdapter foodAdapter;
    EditText search;
    List<Menu> filterList = new ArrayList<>();

    RecyclerView recyclerViewFood, recyclerViewDrink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store, container, false);
        recyclerViewFood = root.findViewById(R.id.food_recycler);
        search = root.findViewById(R.id.search_bar);
        menuList = new ArrayList<>();

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
                if(s.toString().isEmpty()){
                    recyclerViewFood.setAdapter(new StoreVerticleAdapter(getActivity(),menuList));
                    foodAdapter.notifyDataSetChanged();
                }else{
                    Filter(s.toString());
                }
            }
        });

        foodAdapter = new StoreVerticleAdapter(getActivity(),menuList);
        setRecyclerViewFood();
        return root;
    }

    private void setRecyclerViewFood(){
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recyclerViewFood.setHasFixedSize(true);
        recyclerViewFood.setNestedScrollingEnabled(false);
        recyclerViewFood.setAdapter(foodAdapter);
    }

    private void Filter(String text) {
        for(Menu menu : menuList){
            if(menu.getTitle().contains(text)){
                filterList.add(menu);
            }
        }
        recyclerViewFood.setAdapter(new StoreVerticleAdapter(getActivity(),filterList));
        foodAdapter.notifyDataSetChanged();
    }


}