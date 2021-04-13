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
import android.location.Address;
import android.location.Geocoder;
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
    static String address = "";
    static Boolean startup;
    static Double lat1,long1;
    static Double restLat, restLon;
    static String distance;
    static String duration;
    static String currentUser;



    List<Popular> popularFood;
    List <Recommended> recommended;
    List <Menu> menus;

    PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        popularFood = new ArrayList<>();

        recommended = new ArrayList<>();

        menus = new ArrayList<>();

        Intent intent = getIntent();
        String action = "";

        if(intent.getAction() != null)
            action = intent.getAction();

        loadRestaurantData();

        if(action.equals("false"))
            startup = false;

        if(startup)
            loadUserData(currentUser);


        loadData();

        getPopularData(popularFood);
        getRecommendedData(recommended);
        getMenu(menus);

        startup = false;

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


    private  void loadUserData(String username)
    {
       FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference("Users");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                login = snapshot.child(username).getValue(User.class);
                address = login.getAddress();
                if (login.getAddress().equals("") || login.getAddress() == null)
                    getAddressDialog(login);
                locationData();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getMessage());

            }
        });

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

    private void locationData()
    {
        Location origin;
        if(lat1 == null || long1 == null)
        {
            LatLng latLng = getLocationFromAddress(getBaseContext(),address);
            origin = new Location("");
            origin.setLatitude(latLng.latitude);
            origin.setLongitude(latLng.longitude);
        }
        else
        {
            origin = new Location("");
            origin.setLatitude(lat1);
            origin.setLongitude(long1);
        }

        //Add restuarants coordinates here
        Location destination = new Location("");
        destination.setLatitude(Home.restLat);
        destination.setLongitude(Home.restLon);

        String url = getDirectionsUrl(origin, destination);

        DownloadTask downloadTask = new DownloadTask();


        // Start downloading json data from Google Directions API
        downloadTask.execute(url);

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users1 = database.getReference("Users");


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = this.getLayoutInflater().inflate(R.layout.address_dialog, null);
        builder.setView(view);
        if(address.equals(""))
            builder.setMessage("Please Add an Address");
        else
            builder.setMessage("Type in a new address")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if(user != null)
                            {
                                user.setAddress(address);
                                users1.child(user.getEmail()).setValue(user);

                            }
                            else
                            {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference users = database.getReference("Users");

                                users.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        login = snapshot.child(currentUser).getValue(User.class);
                                        login.setAddress(address);
                                        users1.child(login.getEmail()).setValue(login);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        System.out.println("The read failed: " + error.getMessage());

                                    }
                                });
                            }

                            Location origin = new Location("");
                            origin.setLatitude(lat1);
                            origin.setLongitude(long1);

                            //Add restuarants coordinates here
                            Location destination = new Location("");
                            destination.setLatitude(restLat);
                            destination.setLongitude(restLon);

                            String url = getDirectionsUrl(origin, destination);

                            DownloadTask downloadTask = new DownloadTask();

                            // Start downloading json data from Google Directions API
                            downloadTask.execute(url);

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

    private String getDirectionsUrl(Location origin, Location dest){

        // Origin of route
        String str_origin = "origin="+origin.getLatitude()+","+origin.getLongitude();

        // Destination of route
        String str_dest = "destination="+dest.getLatitude()+","+dest.getLongitude();

        // Sensor enabled
        String sensor = "sensor=false";

        String mode = "mode=driving";

        String key = "key="+getResources().getString(R.string.api_key);

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor + "&" + mode + "&" + key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while ", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {


            distance = DirectionsJSONParser.distance;
            duration = DirectionsJSONParser.duration;

            Toast.makeText(getBaseContext(),distance + "  " + duration , Toast.LENGTH_SHORT).show();


        }
    }


    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );


        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}