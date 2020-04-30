package com.esquared.restaurantroulette;

import android.net.Uri;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class GetRestaurant extends AsyncTask<String, Void, Void> {

URL url;
HttpsURLConnection connection;
InputStream input;
String line = "";
String data = "";
JSONObject jo;
JSONArray results;
String name;
JSONObject restaurant;
JSONObject jsonobject;
String RestaurantToGet;
String apiKey;

    @Override
        protected Void doInBackground(String... params) {
        Uri.Builder builder = Uri.parse("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant").buildUpon();
        builder.appendQueryParameter("key", params[1]);

        RestaurantToGet = params[0];
        ;
        try {
            url = new URL(builder.toString());
            Log.i("URL", "saved "+url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("URL", "URL FAILED " + e.getMessage());
            return null;
        }

        try {
            connection = (HttpsURLConnection) url.openConnection();
            Log.i("CONNECTION","Connected " + url.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("CONNECTION", "Failed to connect " + url.toString() + " " + e.getMessage());
            return null;
        }

        try {
            input = connection.getInputStream();
            Log.i("Input", "INPUT STREAM SUCCESSFUL");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Input", "InputStream creation failed " + e.getMessage());
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while(line != null){
            try {
                line = reader.readLine();
                data = data+line;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("DATA", "Failed to populate Data " + e.getMessage());
                return null;
            }
        }
        try {
            jo = new JSONObject(data);
            results = jo.getJSONArray("results");
            Log.i("JSONOBJECT", "Created JSONObject and array, array length " + results.length());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONOBJECT", "failed to create JSONObject " + e.getMessage());
            return null;
        }
        Log.i("DATA", "Size of Data " + data.length());

        for(int i=0; i < 1; i++){
            try {
                jsonobject = results.getJSONObject(Integer.valueOf(RestaurantToGet));
                Log.i("RESTAURANT FIRST LINE", "Get JSONObject");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("RESTAURANT FIRST LINE", "Failed to get JSONObject " + e.getMessage());
            }
            for(int j=0; j<1; j++){
                try {
                    JSONObject restaurant = results.getJSONObject(Integer.valueOf(RestaurantToGet));
                    name = restaurant.get("name").toString();
                    Log.i("Restaurant", "Created JSON Restaurant " + name);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Restaurant", "Failed to create restaurant");
                }
            }
        }
        return null;
    };
}
