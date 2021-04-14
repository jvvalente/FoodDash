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


        Food food = new Food();

        food.loadItems();

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

}