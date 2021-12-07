package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fooddash.model.Food;
import com.example.fooddash.model.Restaurant;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class AdminPanel extends AppCompatActivity {

    ImageView restPic;
    TextView restName, restAddress, restHours;
    Button registerRestaurantButton, addItemButton, returnButton;

    Restaurant rest1;

    FirebaseDatabase database;
    DatabaseReference rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        restPic = findViewById(R.id.restaurantProfilePic);
        restName = findViewById(R.id.textViewRestName);
        restAddress = findViewById(R.id.textViewRestAddress);
        restHours = findViewById(R.id.textViewRestHours);

        registerRestaurantButton = findViewById(R.id.registerRestaurantButton);
        addItemButton = findViewById(R.id.addItemButton);
        returnButton = findViewById(R.id.returnButton);

        //loads restaurant profile
        loadInfo();

        //On click listeners for buttons
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

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLogin();
            }
        });
    }

    private void loadInfo(){

        //Gets restaurant table
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference rest = database2.getReference("Restaurant");

        rest.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    //Gets restaurant object
                    rest1 = postSnapshot.getValue(Restaurant.class);

                    //Sets profile info
                    restName.setText(rest1.getRestaurantName());
                    restAddress.setText(rest1.getRestaurantOpenTime());
                    restHours.setText(rest1.getRestaurantCloseTime() + " - " + rest1.getRestaurantAddress());

                    //Hides button
                    if(!restName.getText().toString().equals(" REGISTER")) {
                        registerRestaurantButton.setVisibility(View.INVISIBLE);
                    }

                    //Try catch used to open and set profile picture
                    try {
                        int SDK_INT = android.os.Build.VERSION.SDK_INT;
                        if (SDK_INT > 8)
                        {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            //Opens url and sets bitmap
                            URL newurl = new URL(rest1.getRestaurantLogoUrl());
                            Bitmap imagePic = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            restPic.setImageBitmap(imagePic);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });


    }

    //Function to open other pages
    private void openRegisterRestaurant(){
        Intent intent = new Intent(this, RegisterRestaurant.class);
        startActivity(intent);
    }

    private void openAddItem(){
        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);
    }

    private void returnLogin(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}