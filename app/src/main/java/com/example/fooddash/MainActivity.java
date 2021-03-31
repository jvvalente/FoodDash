package com.example.fooddash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.fooddash.model.Food;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Food food = new Food();

        //food.loadItems();

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }

}