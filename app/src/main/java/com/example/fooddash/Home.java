package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.LinearLayout;

import com.example.fooddash.adapter.MenuAdapter;
import com.example.fooddash.adapter.PopularAdapter;
import com.example.fooddash.adapter.RecommendedAdapter;
import com.example.fooddash.model.Food;
import com.example.fooddash.model.Menu;
import com.example.fooddash.model.Popular;
import com.example.fooddash.model.Recommended;

import com.example.fooddash.model.Restaurant;
import com.example.fooddash.model.User;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {

    RecyclerView popularRecyclerView, recommendedRecyclerView, menuRecycleView;

    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    MenuAdapter menuAdapter;
    Button signUpButton;
    EditText search;
    User login;
    FirebaseDatabase database;
    DatabaseReference users;
    static String address;
    static Boolean startup;
    static Double lat1,long1;
    static Double restLat, restLon;



    List<Popular> popularFood;
    List <Recommended> recommended;
    List <Menu> menus;

    PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent i = getIntent();
        String currUser = i.getStringExtra("currentUser");

        popularFood = new ArrayList<>();

        recommended = new ArrayList<>();

        menus = new ArrayList<>();
        if(startup)
        {
            loadUserData(currUser);
            startup = false;

        }
        loadData();
        loadRestaurantData();
        getPopularData(popularFood);
        getRecommendedData(recommended);
        getMenu(menus);


    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.editAddress:
                getAddressDialog(login);
                break;


            case R.id.logout:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }


    private void getMenu(List <Menu> menuList){

        menuRecycleView = findViewById(R.id.menu_recycler);
        menuAdapter = new MenuAdapter(this, menuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        menuRecycleView.setLayoutManager(layoutManager);
        menuRecycleView.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();

    }
    private void  getPopularData(List<Popular> popularList){

        popularRecyclerView = findViewById(R.id.popular_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularAdapter = new PopularAdapter(this, popularList);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);

    }

    private void  getRecommendedData(List<Recommended> recommendedList){

        recommendedRecyclerView = findViewById(R.id.recommended_recycler);
        recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

    }
    private void loadRestaurantData()
    {
        //Getting restaurant coordinates
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference rest = database2.getReference("Restaurant");

        rest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Restaurant rest1 = postSnapshot.getValue(Restaurant.class);
                    System.out.println(rest1.getRestaurantLatitude());
                    restLat = rest1.getRestaurantLatitude();
                    restLon = rest1.getRestaurantLongitude();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });

    }
    private  void loadUserData(String username)
    {
       database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                login = snapshot.child(username).getValue(User.class);
                address = login.getAddress();
                if (login.getAddress().equals("") || login.getAddress() == null)
                    getAddressDialog(login);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getMessage());

            }
        });

    }
    private void loadData(){
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference food = database2.getReference("Food");

        food.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                popularFood.clear();
                recommended.clear();
                menus.clear();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Food item = postSnapshot.getValue(Food.class);
                    System.out.println(item.getFoodName());
                    popularFood.add(new Popular(item.getFoodName(),String.valueOf(item.getFoodPrice()), String.valueOf(item.getFoodRating()), String.valueOf(item.getDeliveryTime()), String.valueOf(item.getDeliveryCharges()), item.getFoodNote(), item.getFoodImageUrl()));
                    recommended.add(new Recommended(item.getFoodName(),String.valueOf(item.getFoodPrice()), String.valueOf(item.getFoodRating()), String.valueOf(item.getDeliveryTime()), String.valueOf(item.getDeliveryCharges()), item.getFoodNote(), item.getFoodImageUrl()));
                    menus.add(new Menu(item.getFoodName(),String.valueOf(item.getFoodPrice()), String.valueOf(item.getFoodRating()), String.valueOf(item.getDeliveryTime()), String.valueOf(item.getDeliveryCharges()), item.getFoodNote(), item.getFoodImageUrl()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });

    }

    public void shop(View v) {
        Intent intent = new Intent(this, ShoppingCart.class);
        startActivity(intent);
    }

    private void getAddressDialog(User user)
    {
        users = database.getReference("Users");


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = this.getLayoutInflater().inflate(R.layout.address_dialog, null);
        builder.setView(view);
        if(user.getAddress().equals("") || user.getAddress() == null)
            builder.setMessage("Please Add an Address");
        else
            builder.setMessage("Type in a new address")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            user.setAddress(address);
                            users.child(user.getEmail()).setValue(user);


                        }
                    });
                builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        //Initializing places with api key
        String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }


        // Create a new Places client instance.
        placesClient = Places.createClient(this);


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();


                address = place.getAddress();
                System.out.println("ID " + place.getId());
                System.out.println("NAME " + place.getName());
                System.out.println("ADDRESS " + place.getAddress());
                System.out.println("LAT " + place.getLatLng().latitude);
                System.out.println("LON " + place.getLatLng().longitude);
                lat1 = place.getLatLng().latitude;
                long1 = place.getLatLng().longitude;
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}