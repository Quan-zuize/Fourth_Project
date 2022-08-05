package com.example.project_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_4.Helper.ManagementCart;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import model.Menu;

public class DetailsActivity extends AppCompatActivity {
    ImageButton minusImg, plusImg;
    ImageView menuImg;
    TextView textView, menuPrice, menuName;
    public int quantity;
    String foodId = "";
    Button btnCart;

    FirebaseDatabase database;
    DatabaseReference menus;

    Menu currentFood;
    ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Firebase
//        database = FirebaseDatabase.getInstance();
//        menus = database.getReference("Menu");

        managementCart = new ManagementCart(this);

        menuImg = findViewById(R.id.menu_img);
        menuPrice = findViewById(R.id.textPrice);
        menuName = findViewById(R.id.menu_name);
        btnCart = findViewById(R.id.button);



        minusImg = findViewById(R.id.imageButton1);
        plusImg = findViewById(R.id.imageButton2);
        textView = findViewById(R.id.textView7);
        quantity = Integer.parseInt(textView.getText().toString());

        //Get Food from Intent
        getBundle();
    }

    private void getBundle(){
        currentFood = (Menu) getIntent().getSerializableExtra("object");

        Picasso.get().load(currentFood.getImage_src()).into(menuImg);
        menuPrice.setText(String.valueOf(currentFood.getPrice()));
        menuName.setText(currentFood.getTitle());

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new Database(getBaseContext()).addToCart(new OrderDetail(Integer.parseInt(foodId),quantity,currentFood.getPrice()
//                ));
                currentFood.setNumInCart(quantity);
                managementCart.insertFood(currentFood);
                Toast.makeText(DetailsActivity.this,"Added to Cart",Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void getDetailMenu(String foodId) {
//        menus.child(foodId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                currentFood = snapshot.getValue(Menu.class);
//
//                assert currentFood != null;
//                Picasso.get().load(currentFood.getImage_src()).into(menuImg);
//                menuPrice.setText(String.valueOf(currentFood.getPrice()).concat(" vnđ"));
//                menuName.setText(currentFood.getTitle());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

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