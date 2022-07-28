package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.project_4.adapters.StoreAdapter;
import com.example.project_4.ui.OrderFragment;
import com.example.project_4.ui.CartFragment;
import com.example.project_4.ui.ProfileFragment;
import com.example.project_4.ui.StoreFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Store_dashboardActivity extends AppCompatActivity {
    private ChipNavigationBar chipNavigationBar;
    StoreAdapter storeAdapter;
    RecyclerView foodRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_store_dashboard);

        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.navigation_shop,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new StoreFragment()).commit();
        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.navigation_shop:
                        fragment = new StoreFragment();
                        break;
                    case R.id.navigation_order:
                        fragment = new CartFragment();
                        break;
                    case R.id.navigation_noti:
                        fragment = new OrderFragment();
                        break;
                    case R.id.navigation_profile:
                        fragment = new ProfileFragment();
                        break;
                }
                assert fragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }

//    private void setMenuRecycler(List<Menu> menuList) {
//        foodRecycler = findViewById(R.id.food_recycler);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
//        foodRecycler.setLayoutManager(layoutManager);
//        storeAdapter = new StoreAdapter(this, menuList);
//        foodRecycler.setAdapter(storeAdapter);
//    }


    private void loadMenu(){

    }
}