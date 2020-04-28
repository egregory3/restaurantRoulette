package com.esquared.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class GetPrefs extends AppCompatActivity {
Animation animation;
ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_prefs);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        button = findViewById(R.id.btn_rouletteWheel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            //Future use
            public void onClick(View v) {
                //Future Use
            }
        });
    }


}
