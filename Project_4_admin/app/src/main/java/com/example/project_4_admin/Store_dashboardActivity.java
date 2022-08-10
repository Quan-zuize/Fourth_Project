package com.example.project_4_admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Store_dashboardActivity extends AppCompatActivity {
    private ChipNavigationBar chipNavigationBar;
//    StoreVerticleAdapter storeVerticleAdapter;
    RecyclerView foodRecycler;

    SharedPreferences sharedPreferences;
    private static String SHARED_PREF_NAME = "myPref";
    private static String KEY_PASS = "pass";
    private static String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_store_dashboard);

        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.navigation_shop, true);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StoreFragment()).commit();


        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.navigation_shop:
//                        fragment = new StoreFragment();
                        break;
                    case R.id.navigation_order:
                        if (!checkLogin()) {
//                            fragment = new OrderFragment();
                        } else {
                            redirect();
                        }
                        break;
                    case R.id.navigation_cart:
//                        if (!checkLogin()) {
//                        fragment = new CartFragment();
//                        } else {
//                            redirect();
//                        }
                        break;
                    case R.id.navigation_profile:
//                        fragment = new ProfileFragment();
                        break;
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
            }
        });
    }

    private boolean checkLogin() {
        String pass = sharedPreferences.getString(KEY_PASS, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        return (pass == null || email == null);
    }

    private void redirect(){
//        BackHomeDialog backHomeDialog = new BackHomeDialog();
//        backHomeDialog.show(getSupportFragmentManager(), "back home dialog");
    }

//    private void setMenuRecycler(List<Menu> menuList) {
//        foodRecycler = findViewById(R.id.food_recycler);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
//        foodRecycler.setLayoutManager(layoutManager);
//        storeAdapter = new StoreAdapter(this, menuList);
//        foodRecycler.setAdapter(storeAdapter);
//    }
}