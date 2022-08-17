package com.example.project_4_admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_4_admin.model.Menu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    ImageButton minusImg, plusImg;
    ImageView menuImg, backImg;
    TextView textView, menuPrice, menuName, menuDes, comboFood;
    public int quantity;
    Button btnCart;

    FirebaseDatabase database;
    DatabaseReference menus;

    Menu currentFood;
//    ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Firebase
        database = FirebaseDatabase.getInstance();
        menus = database.getReference("Menu");

//        managementCart = new ManagementCart(this);

        menuImg = findViewById(R.id.menu_img);
        menuPrice = findViewById(R.id.menu_price);
        menuName = findViewById(R.id.menu_name);
        comboFood = findViewById(R.id.combo_food);
        menuDes = findViewById(R.id.menu_des);
//        btnCart = findViewById(R.id.button);
//
//        minusImg = findViewById(R.id.imageButton1);
//        plusImg = findViewById(R.id.imageButton2);
//        textView = findViewById(R.id.numCart);
        quantity = Integer.parseInt(textView.getText().toString());

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
        menuDes.setText(description);

//        btnCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                currentFood.setNumInCart(quantity);
//                managementCart.insertFood(currentFood);
//                Toast.makeText(DetailsActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

//    public void decreaseNum(View view) {
//        quantity = Integer.parseInt(textView.getText().toString());
//        if (quantity == 1) {
//            minusImg.setEnabled(false);
//        } else if (quantity < 11) {
//            textView.setText("0".concat(String.valueOf(quantity - 1)));
//        } else {
//            textView.setText(String.valueOf(quantity - 1));
//        }
//        view.refreshDrawableState();
//    }
//
//    public void increaseNum(View view) {
//        quantity = Integer.parseInt(textView.getText().toString());
//        if (quantity == 1) minusImg.setEnabled(true);
//        if (quantity < 9) {
//            textView.setText("0".concat(String.valueOf(quantity + 1)));
//        } else {
//            textView.setText(String.valueOf(quantity + 1));
//        }
//        view.refreshDrawableState();
//    }
}