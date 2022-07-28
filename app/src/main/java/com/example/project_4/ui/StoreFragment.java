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
import com.example.project_4.adapters.StoreAdapter;

import java.util.ArrayList;
import java.util.List;

import model.Menu;

public class StoreFragment extends Fragment {
    RecyclerView storeRec;
    List<Menu> menuList;
    StoreAdapter storeAdapter;
    EditText search;
    List<Menu> filterList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store, container, false);
        storeRec = root.findViewById(R.id.food_recycler);
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
                    storeRec.setAdapter(new StoreAdapter(getActivity(),menuList));
                    storeAdapter.notifyDataSetChanged();
                }else{
                    Filter(s.toString());
                }
            }
        });

        storeAdapter = new StoreAdapter(getActivity(),menuList);
        storeRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        storeRec.setHasFixedSize(true);
        storeRec.setNestedScrollingEnabled(false);
        return root;
    }

    private void Filter(String text) {
        for(Menu menu : menuList){
            if(menu.getTitle().contains(text)){
                filterList.add(menu);
            }
        }
        storeRec.setAdapter(new StoreAdapter(getActivity(),filterList));
        storeAdapter.notifyDataSetChanged();
    }


}