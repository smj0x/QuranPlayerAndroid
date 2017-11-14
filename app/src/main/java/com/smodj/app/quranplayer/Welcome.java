package com.smodj.app.quranplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

/**
 * Created by smj on 11/7/17.
 */

public class Welcome extends AppCompatActivity {
    LinearLayout linearLayout;
    Animation animate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Main View
        setContentView(R.layout.welcome);

        linearLayout = findViewById(R.id.welcomeIcon);
        animate = AnimationUtils.loadAnimation(this, R.anim.dropdown);
        linearLayout.setAnimation(animate);
        final Intent intent = new Intent(this, MainActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(850);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            finally{
                    startActivity(intent);
                    finish();
            }
            }
        };
        timer.start();
    }
}
