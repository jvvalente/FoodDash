package com.example.fooddash;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.String;

import android.widget.TextView;

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


}
