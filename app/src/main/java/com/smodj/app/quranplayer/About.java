package com.smodj.app.quranplayer;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by smj on 11/3/17.
 */

public class About extends AppCompatActivity {
    LinearLayout linearLayout;
    Animation animate;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        setActionBar("About");
        linearLayout = findViewById(R.id.welcomeIcon);
        logo = findViewById(R.id.aboutLogo);
        animate = AnimationUtils.loadAnimation(this, R.anim.dropdown);
        logo.setAnimation(animate);

    }
    public void aboutLogo(View view){
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2500);
        rotate.setInterpolator(new LinearInterpolator());
        view.startAnimation(rotate);
        Snackbar.make(view, R.string.dev,
                Snackbar.LENGTH_LONG)
                .show();
    }
    public void setActionBar(String heading) {
        // TODO Auto-generated method stub

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle(heading);
        actionBar.show();
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
