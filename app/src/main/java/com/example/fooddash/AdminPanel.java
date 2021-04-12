package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fooddash.model.Food;
import com.example.fooddash.model.Restaurant;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPanel extends AppCompatActivity {

    TextView restName, restAddress, restHours;
    Button registerRestaurantButton, addItemButton;

    FirebaseDatabase database;
    DatabaseReference rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        restName = findViewById(R.id.textViewRestName);
        restAddress = findViewById(R.id.textViewRestAddress);
        restHours = findViewById(R.id.textViewRestHours);

        registerRestaurantButton = findViewById(R.id.registerRestaurantButton);
        addItemButton = findViewById(R.id.addItemButton);

        //loadInfo();

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

    private void loadInfo(){

        database = FirebaseDatabase.getInstance();
        rest = database.getReference("Restaurant");

        rest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Restaurant restaurant = snapshot.child("Restaurant").child("test").getValue(Restaurant.class);
                System.out.println(restaurant.getRestaurantName() + " " + restaurant.getRestaurantAddress());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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