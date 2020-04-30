package com.esquared.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class displayActivity extends Restaurant {

    ArrayList<Restaurant> restaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String apiKey = (String) getText(R.string.places_api_key);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        //using for now to pass a value to pull restaurant number #
        String[] myparams = new String[]{String.valueOf(5), apiKey};
        //Use GetRestaurant class to generate a restaurant
        GetRestaurant restaurant = new GetRestaurant();
         restaurant.execute(myparams);
    }
}
