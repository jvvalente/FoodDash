package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.fooddash.model.Food;
import com.example.fooddash.model.Menu;
import com.example.fooddash.model.Popular;
import com.example.fooddash.model.Recommended;
import com.example.fooddash.model.Restaurant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference rest = database2.getReference("Restaurant");

        rest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Restaurant rest1 = postSnapshot.getValue(Restaurant.class);
                    System.out.println("Please work " + rest1.getRestaurantName() + " " + rest1.getRestaurantLatitude() + " " + rest1.getRestaurantLongitude());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });

        Food food = new Food();

        food.loadItems();

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

}