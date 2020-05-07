
package com.esquared.restaurantroulette;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class DisplayActivity extends AppCompatActivity {
    Animation animation;
    ImageView animatedTV;
    ArrayList<Restaurant> restaurants;
    private FusedLocationProviderClient client;
    TextView output;
    TextView tvAddress;
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String apiKey = (String) getText(R.string.places_api_key);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotateinfinite);
        animatedTV = findViewById(R.id.wheel_include);
        //using for now to pass a value to pull restaurant number #
        client = LocationServices.getFusedLocationProviderClient(this);

        client.getLastLocation().addOnSuccessListener(DisplayActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    String latitude = String.valueOf(location.getLatitude());
                    String longitude = String.valueOf(location.getLongitude());
                    Log.i("Location", latitude + "/" + longitude);
                    String[] myparams = new String[]{String.valueOf(5), apiKey, latitude, longitude};
                    //Use GetRestaurant class to generate a restaurant
                    GetRestaurant restaurant = new GetRestaurant();
                    restaurant.execute(myparams);
                }
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        animatedTV.startAnimation(animation);
    }

    class GetRestaurant extends AsyncTask<String, Void, ArrayList<Restaurant>> {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        Restaurant curRestaurant;
        String placeId;
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
        String restaurantToGet;
        String apiKey;
        double lat, lng;
        Uri.Builder detailBuilder;
        Boolean isOpen;
        String address;

        @Override
        protected ArrayList<Restaurant> doInBackground(String... params) {
            Uri.Builder builder = Uri.parse("https://maps.googleapis.com/maps/api/place/nearbysearch/json").buildUpon();
            builder.appendQueryParameter("location", params[2] + "," + params[3]);
            builder.appendQueryParameter("radius", "5000");
            builder.appendQueryParameter("type", "restaurant");
            builder.appendQueryParameter("key", params[1]);



            try {
                url = new URL(builder.toString());
                Log.i("URL", "saved "+url.toString());
                connection = (HttpsURLConnection) url.openConnection();
                Log.i("CONNECTION","Connected " + url.toString());
                input = connection.getInputStream();
                Log.i("Input", "INPUT STREAM SUCCESSFUL");
            } catch (IOException e) {
                Log.e("Input", "InputStream creation failed " + e.getMessage());
                return null;
            }

            BufferedReader reader2 = new BufferedReader(new InputStreamReader(input));
            data = "";
            while(line != null){
                try {
                    line = reader2.readLine();
                    data = data+line;
                } catch (IOException e1) {
                    Log.e("DATA", "Failed to populate Data " + e1.getMessage());
                    return null;
                }
            }
            BufferedReader reader;
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
                    jsonobject = results.getJSONObject(Integer.valueOf(i));
                    Log.i("RESTAURANT FIRST LINE", "Get JSONObject");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("RESTAURANT FIRST LINE", "Failed to get JSONObject " + e.getMessage());
                }
                for(int j=0; j<results.length(); j++){
                    try {
                        JSONObject hours;
                        isOpen = false;
                        restaurant = results.getJSONObject(Integer.valueOf(j));

                        name = restaurant.get("name").toString();
                        placeId = restaurant.get("place_id").toString();
                        JSONObject geometry;
                        geometry = (JSONObject) restaurant.get("geometry");
                        JSONObject location = (JSONObject) geometry.get("location");

                        lat = location.getDouble("lat");
                        lng = location.getDouble("lng");
                        try {
                            hours = (JSONObject) restaurant.get("opening_hours");
                            isOpen = (Boolean) hours.get("open_now");
                        }catch(org.json.JSONException e){
                            Log.i("isOpen", "Unable to set isOpen");
                        }

                        restaurants.add(new Restaurant(name, placeId, lat, lng, isOpen));
                        Log.i("Restaurant", "Created JSON Restaurant " + name + " " + lat + "/" +lng);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("Restaurant", "Failed to create restaurant");
                    }
                }
            }

            for(Restaurant r: restaurants) {

                detailBuilder = Uri.parse("https://maps.googleapis.com/maps/api/place/details/json").buildUpon();
                detailBuilder.appendQueryParameter("place_id", r.getPlaceId());
                detailBuilder.appendQueryParameter("key", params[1]);

                try {
                    URL detailURL = new URL(detailBuilder.toString());
                    connection = (HttpsURLConnection) detailURL.openConnection();
                    input = connection.getInputStream();
                    BufferedReader reader1 = new BufferedReader(new InputStreamReader(input));
                    data = "";
                    line = "";
                    while(line != null){
                        try {
                            line = reader1.readLine();
                            data = data+line;
                        } catch (IOException e) {
                            Log.e("DATA", "Failed to populate Data " + e.getMessage());
                            return null;
                        }
                    }

                    jo = new JSONObject(data);
                    restaurant = jo.getJSONObject("result");
                    r.setAddress(restaurant.get("formatted_address").toString());
                    r.setPhone(restaurant.get("formatted_phone_number").toString());
                    r.setRating( restaurant.getDouble("rating"));
                    r.setPrice((int) restaurant.get("price_level"));
                    Log.i("Restaurant", "" + r.getName() + " "+ r.getFormattedAddress() + " " + r.getLattitude() + "/" + r.getLongitude() + " " + r.getRating() + " " + r.getPrice());


                } catch (MalformedURLException e) {
                    Log.i("RESTAURANT", ""+e.getMessage());
                } catch (IOException e) {
                    Log.i("RESTAURANT", ""+e.getMessage());
                } catch (JSONException e) {
                    Log.i("RESTAURANT", ""+e.getMessage());
                }
            }
            return restaurants;
        }

        @Override
        protected void onPostExecute(ArrayList<Restaurant> restaurants) {
            Restaurant restaurant = new Restaurant();
            super.onPostExecute(restaurants);
            boolean isOpen = false;
            while(isOpen == false) {
                Integer random = new Random().nextInt(20);
                restaurant = restaurants.get(random);
                Log.i("Restaurant", restaurant.getName());
                isOpen = restaurant.getOpenNow();
            }

            animatedTV.clearAnimation();
            animatedTV.setVisibility(View.INVISIBLE);
            output = findViewById(R.id.tv_Rname);
            findViewById(R.id.tv_title).setVisibility(View.INVISIBLE);
            tvAddress = findViewById(R.id.tv_Raddress);
            tvPhone = findViewById(R.id.tv_Rphone);
            output.setText(restaurant.getName());
            tvAddress.setText(restaurant.getFormattedAddress());
            tvPhone.setText(restaurant.getFormattedPhone());
            findViewById(R.id.btn_playAgain).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_playAgain).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
                    startActivity(intent);
                }
            });

        }
    }

}
