package com.example.fooddash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPanel extends AppCompatActivity {

    Button registerRestaurantButton, addItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        registerRestaurantButton = findViewById(R.id.registerRestaurantButton);
        addItemButton = findViewById(R.id.addItemButton);

        registerRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterRestaurant();
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddItem();
            }
        });
    }

    private void openRegisterRestaurant(){
        Intent intent = new Intent(this, RegisterRestaurant.class);
        startActivity(intent);
    }

    private void openAddItem(){
        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);
    }
}