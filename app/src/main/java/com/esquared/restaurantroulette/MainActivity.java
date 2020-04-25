package com.esquared.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
Animation animation;
ImageView animatedTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

        animatedTV = findViewById(R.id.iv_animated);

    }
    @Override
   protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        animatedTV.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            //Will play a sound eventually while animation runs
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(getApplicationContext(), theRules.class);
                startActivity(intent);

            }
            //required
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
