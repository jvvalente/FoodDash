package com.example.fooddash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.String;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

public class ShoppingCart extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    ImageView backButton;
    TextView totalPrice;
    static double total = 0.00;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);


        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.setAction("false");
                startActivity(intent);
            }
        });

         listView = (ListView) findViewById(R.id.userList);
         totalPrice = (TextView) findViewById(R.id.totalCartPrice);
         String temp = "Total: $" + total;
         totalPrice.setText(temp);

        adapter = FoodDetails.adapter;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater inflater =getLayoutInflater();
                view = inflater.inflate(R.layout.cart,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Would you like to remove this Item?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String item = adapterView.getItemAtPosition(i).toString();
                                String priceString = "";
                                for(int j = 0; j < item.length(); j++){
                                    char c = item.charAt(j);
                                    if(c == '$'){
                                        priceString = item.substring(j+1);
                                        break;
                                    }

                                }
                                FoodDetails.listItems.remove(i);
                                adapter.notifyDataSetChanged();
                                double integer=Double.parseDouble(priceString);
                                total -= integer;
                                String temp = "Total: $" + total;
                                totalPrice.setText(temp);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                builder.show();

            }
        });

    }


    public void confirmButton(View view) {


        if(FoodDetails.listItems.size() != 0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("\t\t\t\t\t\t\tThe delivery is on its way!\n\t\t\t\t\t\t\t\t\t\t\t\tArriving in " + Home.duration)
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            while (FoodDetails.listItems.size() != 0)
                                FoodDetails.listItems.remove(0);
                            total = 0;
                            Intent intent = new Intent(getBaseContext(), Home.class);
                            intent.setAction("false");
                            startActivity(intent);
                        }
                    });

            final AlertDialog dialog = builder.create();

            dialog.show();


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //create a new one
            layoutParams.weight = (float) 1.0;
            layoutParams.gravity = Gravity.CENTER; //this is layout_gravity
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setLayoutParams(layoutParams);

        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Your Cart Is Empty (Please Add an Item)")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            final AlertDialog dialog = builder.create();

            dialog.show();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //create a new one
            layoutParams.weight = (float) 1.0;
            layoutParams.gravity = Gravity.CENTER; //this is layout_gravity
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setLayoutParams(layoutParams);

        }

    }



}
