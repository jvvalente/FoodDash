package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fooddash.model.Food;
import com.example.fooddash.model.Restaurant;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Objects;

public class RegisterRestaurant extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rest = database.getReference("Restaurant");

    PlacesClient placesClient;

    Restaurant restaurant;

    Double lat, lon;

    EditText restName;
    TimePicker restOpen, restClose;
    Button registerRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);

        //Initializing places with api key
        String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance.
        placesClient = Places.createClient(this);

        //Initializes items
        restName = findViewById(R.id.editTextRestName);
        restOpen = findViewById(R.id.datePickerOpen);
        restClose = findViewById(R.id.datePickerClose);
        registerRest = findViewById(R.id.register_button);

        restOpen.setIs24HourView(true);
        restClose.setIs24HourView(false);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();

                lat = place.getLatLng().latitude;
                lon = place.getLatLng().longitude;

                System.out.println("ID " + place.getId());
                System.out.println("NAME " + place.getName());
                System.out.println("ADDRESS " + place.getAddress());
                System.out.println("LAT " + place.getLatLng().latitude);
                System.out.println("LON " + place.getLatLng().longitude);
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        registerRest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                String openTime = restOpen.getHour() + ":" + restOpen.getMinute();
                String closeTime = restClose.getHour() + ":" + restClose.getMinute();
                restaurant = new Restaurant(restName.getText().toString(), "", openTime, closeTime, lat,lon);

                rest.child(restaurant.getRestaurantName()).setValue(restaurant);
            }
        });

    }
}