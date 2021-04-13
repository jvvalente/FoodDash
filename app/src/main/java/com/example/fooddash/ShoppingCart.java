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
    static String distance;
    static String duration;
    static double total = 0.00;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);


        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Home.class);
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
        Location origin;
        if(Home.lat1 == null || Home.long1 == null)
        {
            LatLng latLng = getLocationFromAddress(getApplicationContext(),Home.address);
            origin = new Location("");
            origin.setLatitude(latLng.latitude);
            origin.setLongitude(latLng.longitude);
        }
        else
        {
            origin = new Location("");
            origin.setLatitude(Home.lat1);
            origin.setLongitude(Home.long1);
        }

        //Add restuarants coordinates here
        Location destination = new Location("");
        destination.setLatitude(Home.restLat);
        destination.setLongitude(Home.restLon);

        String url = getDirectionsUrl(origin, destination);

        DownloadTask downloadTask = new DownloadTask();


        // Start downloading json data from Google Directions API
        downloadTask.execute(url);

        ProgressBar bar = (ProgressBar) findViewById(R.id.indeterminateBar);

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("The delivery is on its way! \n Arriving in " + duration)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });


        builder.show();

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
