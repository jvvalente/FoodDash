package com.example.fooddash;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FoodDetails extends AppCompatActivity {

    ImageView imageView,backButton;
    TextView itemName, itemPrice, itemRating, itemDescription;
    RatingBar ratingBar;
    ListView listView;
    static ArrayAdapter<String> adapter;
    static ArrayList<String> listItems=new ArrayList<String>();

    String name, price, rating, imageUrl, description, imageURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_details);

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        rating = intent.getStringExtra("rating");
        imageUrl = intent.getStringExtra("image");
        description = intent.getStringExtra("description");
        imageURL = intent.getStringExtra("image");

        imageView = findViewById(R.id.imageView5);
        itemName = findViewById(R.id.name);
        itemPrice = findViewById(R.id.price);
        itemRating = findViewById(R.id.rating);
        itemDescription = findViewById(R.id.foodDescription);
        ratingBar = findViewById(R.id.ratingBar);
        backButton = findViewById(R.id.imageView2);

        //listView = findViewById(R.id.userList);

        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here
                URL newurl = new URL(imageURL);
                Bitmap imagePic = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                imageView.setImageBitmap(imagePic);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        itemName.setText(name);
        itemPrice.setText(price);
        itemRating.setText(rating);
        itemDescription.setText(description);
        ratingBar.setRating(Float.parseFloat(rating));

        backButton = (ImageView) findViewById(R.id.imageView2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Home.class);
                startActivity(intent);
            }
        });

    }


    public void shoppingCart(View v)
    {
    Intent intent = new Intent(this,ShoppingCart.class);
        startActivity(intent);
    }
    public void addToCart(View v)
    {
        adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        if(!price.contains("$"))
        listItems.add(name + "\t\t\t\t$" + price);
        else if(price.contains("$"))
            listItems.add(name + "\t\t\t\t" + price);
        if(price.contains("$"))
        price = price.substring(1);
        double i=Double.parseDouble(price);
        ShoppingCart.total += i;
        price = '$' + price;
        adapter.notifyDataSetChanged();
        Toast.makeText(FoodDetails.this, "Added to cart", Toast.LENGTH_SHORT).show();
    }

}



