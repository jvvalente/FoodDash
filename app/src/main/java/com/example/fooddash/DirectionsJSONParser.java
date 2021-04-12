package com.example.fooddash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class DirectionsJSONParser {

    static String duration = "";
    static String distance = "";
    /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
    public void parse(JSONObject jObject){

        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONObject jDistance = null;
        JSONObject jDuration = null;


        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing first routes */
                jLegs = ( (JSONObject)jRoutes.get(0)).getJSONArray("legs");

                List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();


                    /** Getting distance from the json data */
                    jDistance = ((JSONObject) jLegs.get(0)).getJSONObject("distance");
                    distance = jDistance.getString("text");

                    /** Getting duration from the json data */
                    jDuration = ((JSONObject) jLegs.get(0)).getJSONObject("duration");
                    duration = jDuration.getString("text");


        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
        }
    }


}