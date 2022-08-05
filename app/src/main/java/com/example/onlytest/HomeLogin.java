package com.example.onlytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.onlytest.fragment.CartFragment;
import com.example.onlytest.fragment.OrderFragment;
import com.example.onlytest.fragment.UserProfileFragment;
import com.example.onlytest.fragment.ShopFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeLogin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    private static final int SHOP = 0 ;
    private static final int ORDER = 1 ;
    private static final int CART = 2 ;
    private static final int USER = 3 ;

    private int currentFragment = SHOP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.fragment_container_login);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawerLayout ,toolbar ,
                R.string.navigation_open , R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new ShopFragment());
        navigationView.getMenu().findItem(R.id.navigation_shop).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.navigation_shop){
            if(currentFragment != SHOP){
                replaceFragment(new ShopFragment());
                currentFragment = SHOP;
            }
        }else if (id == R.id.navigation_order){
            if(currentFragment != ORDER){
                replaceFragment(new OrderFragment());
                currentFragment = ORDER;
            }
        }
        else if (id == R.id.navigation_cart){
            if(currentFragment != CART){
                replaceFragment(new CartFragment());
                currentFragment = CART;
            }
        } else if (id == R.id.navigation_profile){
            if(currentFragment != USER){
                replaceFragment(new UserProfileFragment());
                currentFragment = USER;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame , fragment);
        transaction.commit();
    }
}