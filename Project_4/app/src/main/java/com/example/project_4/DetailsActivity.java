package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_4.Helper.ManagementCart;
import com.example.project_4.ui.CartFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import com.example.project_4.model.Menu;

public class DetailsActivity extends AppCompatActivity {
    ImageButton minusImg, plusImg;
    ImageView menuImg, backImg;
    TextView textView, menuPrice, menuName, menuDes, comboFood;
    public int quantity;
    Button btnCart;

    FirebaseDatabase database;
    DatabaseReference menus;

    Menu currentFood;
    ManagementCart managementCart;

    SharedPreferences sharedPreferences;
    static String SHARED_PREF_NAME = "myPref";
    static String KEY_ID = "id";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Firebase
        database = FirebaseDatabase.getInstance();
        menus = database.getReference("Menu");

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        menuImg = findViewById(R.id.menu_img);
        menuPrice = findViewById(R.id.menu_price);
        menuName = findViewById(R.id.menu_name);
        menuDes = findViewById(R.id.menu_des);
        btnCart = findViewById(R.id.button);
        comboFood = findViewById(R.id.combo_food);

        minusImg = findViewById(R.id.imageButton1);
        plusImg = findViewById(R.id.imageButton2);
        textView = findViewById(R.id.numCart);

        backImg = findViewById(R.id.imageView7);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Get Food from Intent
        getBundle();
    }

    private void getBundle() {
        currentFood = (Menu) getIntent().getSerializableExtra("object");
        String description = currentFood.getDescription();
        if (currentFood.getCategory().equals("Combo")){
            comboFood.setVisibility(View.VISIBLE);
            String[] parts = description.split("\n");
            comboFood.setText(parts[0].replaceAll("(^\\[|\\]$)", "").trim());
            description = parts[1];
        }
        Picasso.get().load(currentFood.getImage_src()).into(menuImg);
        menuPrice.setText(String.format("%,.0f", currentFood.getPrice()).concat(" đ"));
        menuName.setText(currentFood.getTitle());
        //menuDes.setText(currentFood.getDescription());
        menuDes.setText(description);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkLogin()){
                    quantity = Integer.parseInt(textView.getText().toString());
                    currentFood.setNumInCart(quantity);
                    managementCart = new ManagementCart(getBaseContext(),id);
                    managementCart.insertFood(currentFood);
                    Toast.makeText(DetailsActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                }else{
                    redirect();
                }
            }
        });
    }

    public void decreaseNum(View view) {
        quantity = Integer.parseInt(textView.getText().toString());
        if (quantity == 1) {
            minusImg.setEnabled(false);
        } else if (quantity < 11) {
            textView.setText("0".concat(String.valueOf(quantity - 1)));
        } else {
            textView.setText(String.valueOf(quantity - 1));
        }
        view.refreshDrawableState();
    }

    public void increaseNum(View view) {
        quantity = Integer.parseInt(textView.getText().toString());
        if (quantity == 1) minusImg.setEnabled(true);
        if (quantity < 9) {
            textView.setText("0".concat(String.valueOf(quantity + 1)));
        } else {
            textView.setText(String.valueOf(quantity + 1));
        }
        view.refreshDrawableState();
    }

    private boolean checkLogin() {
        id = sharedPreferences.getString(KEY_ID, null);
        return (id == null);
    }

    private void redirect() {
        BackHomeDialog backHomeDialog = new BackHomeDialog();
        backHomeDialog.show(getSupportFragmentManager(), "back home dialog");
    }
}