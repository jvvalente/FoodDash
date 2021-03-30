package com.example.fooddash;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
    static ArrayList<String> listItems=new ArrayList<String>();

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

        listView = findViewById(R.id.userList);

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
        adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listItems.add(name + "\t\t" + price);
        price = price.substring(1);
        int i=Integer.parseInt(price);
        ShoppingCart.total += i;
        price = '$' + price;
        adapter.notifyDataSetChanged();
        Toast.makeText(FoodDetails.this, "Added to cart", Toast.LENGTH_SHORT).show();
    }

}



