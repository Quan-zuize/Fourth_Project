package com.example.project_4_admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.project_4_admin.service.ListenOrder;
import com.example.project_4_admin.ui.OrderManagerFragment;
import com.example.project_4_admin.ui.ProfileManagerFragment;
import com.example.project_4_admin.ui.StoreFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import com.example.project_4_admin.BackHomeDialog;
import com.example.project_4_admin.R;
import com.example.project_4_admin.ui.StoreFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Manager_dashboardActivity extends AppCompatActivity {
    private ChipNavigationBar chipNavigationBar;

    SharedPreferences sharedPreferences;
    private static String SHARED_PREF_NAME = "myPref";
    private static String KEY_PASS = "pass";
    private static String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_manager_dashboard);

        chipNavigationBar = findViewById(R.id.bottom_nav_admin_menu);
        chipNavigationBar.setItemSelected(R.id.navigation_shop, true);

        if (savedInstanceState == null) {
            chipNavigationBar.setItemSelected(R.id.navigation_shop_mana, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new StoreFragment()).commit();

        }
        //Service
        Intent service = new Intent(Manager_dashboardActivity.this, ListenOrder.class);
        startService(service);
        
        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.navigation_shop_mana:
                        fragment = new StoreFragment();
                        break;
                    case R.id.navigation_order_mana:
                        if (!checkLogin()) {
                            fragment = new OrderManagerFragment();
                        } else {
                            redirect();
                        }
                        break;
                    case R.id.navigation_cart_mana:
//                        if (!checkLogin()) {
//                            fragment = new CartFragment();
//                        } else {
//                            redirect();
//                        }
                        break;
                    case R.id.navigation_profile_mana:
                        fragment = new ProfileManagerFragment();
                        break;
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin
                            , fragment).commit();
                }
            }
        });
    }

    private boolean checkLogin() {
        String pass = sharedPreferences.getString(KEY_PASS, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        return (pass == null || email == null);
    }

    private void redirect() {
        BackHomeDialog backHomeDialog = new BackHomeDialog();
        backHomeDialog.show(getSupportFragmentManager(), "back home dialog");
    }
}