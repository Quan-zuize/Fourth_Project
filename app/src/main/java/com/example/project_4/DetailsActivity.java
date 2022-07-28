package com.example.project_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.Menu;

public class DetailsActivity extends AppCompatActivity {
    ImageButton minusImg, plusImg;
    ImageView menuImg;
    TextView textView, menuPrice, menuName;
    int quantity;
    String foodId = "";
    Button btnCart;

    FirebaseDatabase database;
    DatabaseReference menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Firebase
//        database = FirebaseDatabase.getInstance();
//        menus = database.getReference("Menu");

        menuImg = findViewById(R.id.menu_img);
        menuPrice = findViewById(R.id.textPrice);
        menuName = findViewById(R.id.menu_name);
        btnCart = findViewById(R.id.button);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        minusImg = findViewById(R.id.imageButton1);
        plusImg = findViewById(R.id.imageButton2);
        textView = findViewById(R.id.textView7);

        if(getIntent() != null){
            foodId = getIntent().getStringExtra("id");
        }
//        if(!foodId.isEmpty()){
//            getDetailMenu(foodId);
//        }
    }

    private void getDetailMenu(String foodId) {
        menus.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Menu menu = snapshot.getValue(Menu.class);

                assert menu != null;
                Picasso.get().load(menu.getImage_src()).into(menuImg);
                menuPrice.setText(String.valueOf(menu.getPrice()));
                menuName.setText(menu.getTitle());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void decreaseNum(View view) {
        quantity = Integer.parseInt(textView.getText().toString());
        if(quantity == 1){
            minusImg.setEnabled(false);
        }else if (quantity < 11){
            textView.setText("0".concat(String.valueOf(quantity-1)));
        }else{
            textView.setText(String.valueOf(quantity-1));
        }
        view.refreshDrawableState();
    }

    public void increaseNum(View view) {
        quantity = Integer.parseInt(textView.getText().toString());
        if(quantity == 1) minusImg.setEnabled(true);
        if (quantity < 9){
            textView.setText("0".concat(String.valueOf(quantity+1)));
        }else{
            textView.setText(String.valueOf(quantity+1));
        }
        view.refreshDrawableState();
    }
}