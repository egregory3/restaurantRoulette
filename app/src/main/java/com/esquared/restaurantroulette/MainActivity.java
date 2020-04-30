package com.esquared.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
Animation animation;
ImageView animatedTV;
MediaPlayer player;
final int mPlayerLength = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup Roulette Wheel Animation
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        animatedTV = findViewById(R.id.iv_animated);
       player = MediaPlayer.create(MainActivity.this, R.raw.rouletteball5second);
       player.setLooping(false);


    }
    @Override
   protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Start Roulette wheel animation and watch it
        animatedTV.startAnimation(animation);
        //AnimationRunner();


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        AnimationRunner();
    }

    private void AnimationRunner() {
        //Set Animation Listener
        animation.setAnimationListener(new Animation.AnimationListener() {
            //Will play a sound eventually while animation runs
            @Override
            public void onAnimationStart(Animation animation) {
                player.seekTo(mPlayerLength);
                player.start();
            }

            //When Animation ends fire next activity
            @Override
            public void onAnimationEnd(Animation animation) {

                Intent intent = new Intent(getApplicationContext(), TheRules.class);
                if(player.isPlaying()){
                    player.stop();
                }
                //Stop sound before firing intent
                startActivity(intent);

            }
            //required
            @Override
            public void onAnimationRepeat(Animation animation) {
                //non-repeating
            }
        });
    }
}
