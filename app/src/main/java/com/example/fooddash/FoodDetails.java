package com.example.fooddash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FoodDetails extends AppCompatActivity {

    ImageView imageView,backButton;
    TextView itemName, itemPrice, itemRating;
    RatingBar ratingBar;
    ListView listView;
    static ArrayAdapter<String> adapter;
    ArrayList<String> listItems=new ArrayList<String>();


    String name, price, rating, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_details);

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        rating = intent.getStringExtra("rating");
        imageUrl = intent.getStringExtra("image");

        imageView = findViewById(R.id.imageView5);
        itemName = findViewById(R.id.name);
        itemPrice = findViewById(R.id.price);
        itemRating = findViewById(R.id.rating);
        ratingBar = findViewById(R.id.ratingBar);
        backButton = findViewById(R.id.imageView2);

        itemName.setText(name);
        itemPrice.setText(price);
        itemRating.setText(rating);
        ratingBar.setRating(Float.parseFloat(rating));

    }

    public void goBack(View v)
    {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void shoppingCart(View v)
    {
    Intent intent = new Intent(this,ShoppingCart.class);
        startActivity(intent);
    }
    public void addToCart(View v)
    {
       // listView = (ListView) findViewById(R.id.list_users);
        adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listItems.add(name + "\t\t" + price);
        adapter.notifyDataSetChanged();
        Toast.makeText(FoodDetails.this, "Added to cart", Toast.LENGTH_SHORT).show();




    }
}

