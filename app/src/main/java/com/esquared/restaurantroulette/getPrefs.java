package com.esquared.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class getPrefs extends AppCompatActivity {
Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_prefs);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
    }


}
