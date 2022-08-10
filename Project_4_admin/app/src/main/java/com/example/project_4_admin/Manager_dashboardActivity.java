package com.example.project_4_admin;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.project_4.BackHomeDialog;
//import com.example.project_4.R;
//import com.example.project_4.adapters.StoreVerticleAdapter;
//import com.example.project_4.ui.CartFragment;
//import com.example.project_4.ui.OrderFragment;
//import com.example.project_4.ui.ProfileFragment;
//import com.example.project_4.ui.StoreFragment;
//import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Manager_dashboardActivity extends AppCompatActivity {
//    private ChipNavigationBar chipNavigationBar;
//    StoreVerticleAdapter storeVerticleAdapter;
//    RecyclerView foodRecycler;
//
//    SharedPreferences sharedPreferences;
////    private static String SHARED_PREF_NAME = "myPref";
////    private static String KEY_PASS = "pass";
////    private static String KEY_EMAIL = "email";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_manager_dashboard);
//
//        chipNavigationBar = findViewById(R.id.bottom_nav_admin_menu);
//        chipNavigationBar.setItemSelected(R.id.navigation_shop, true);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new StoreFragment()).commit();
//
//
//        bottomMenu();
//    }
//
//    private void bottomMenu() {
//        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(int i) {
//                Fragment fragment = null;
//                switch (i) {
//                    case R.id.navigation_shop:
//                        fragment = new StoreFragment();
//                        break;
//                    case R.id.navigation_order:
//                        fragment = new OrderManagerFragment();
//                        break;
//                    case R.id.navigation_menu:
////                        if (!checkLogin()) {
//                        fragment = new CartFragment();
////                        } else {
////                            redirect();
////                        }
//                        break;
//                    case R.id.navigation_manage:
////                        if (!checkLogin()) {
////                            fragment = new ProfileFragment();
////                        } else {
////                            redirect();
////                        }
//                        break;
//                }
//
//                if (fragment != null) {
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
//                }
//            }
//        });
//    }

//    private boolean checkLogin() {
//        String pass = sharedPreferences.getString(KEY_PASS, null);
//        String email = sharedPreferences.getString(KEY_EMAIL, null);
//        return (pass == null || email == null);
//    }
//
//    private void redirect(){
//        BackHomeDialog backHomeDialog = new BackHomeDialog();
//        backHomeDialog.show(getSupportFragmentManager(), "back home dialog");
//    }

}