package com.smodj.app.quranplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String username,language,jsonFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Getting Setting values
        KeyValueHelper key = new KeyValueHelper();
        username = key.getPref("edit_text_preference_1", this);
        language = key.getPref("listLang",this);

        //selecting language

        settinglang(language);

        //Navigation
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Setting Nav View Name
        View navHeaderView = navigationView.getHeaderView(0);
        TextView nav_name = navHeaderView.findViewById(R.id.textViewHeader);
        nav_name.setText(username);
        //Recycler View

        RecyclerView authorsLisRV = findViewById(R.id.authorsListRV);
        authorsLisRV.setLayoutManager(new LinearLayoutManager(this));

        //Bringing Json Data from assets and loading it in to Array of Author Model
        String result = loadJSONFromAsset();
        Author[] data = new Gson().fromJson(result, Author[].class);


        //Set in Recycler View
         authorsLisRV.setAdapter(new AuthorAdapter(MainActivity.this, data));


    }

    private void settinglang(String lang) {
      if(lang == null){
          jsonFile = Constants.jsonEn;
      }else{
        switch (lang) {
            case "English":

                jsonFile = Constants.jsonEn;
                break;
            case "Arabic":
                jsonFile = Constants.jsonAr;
                break;
            default:
                jsonFile = Constants.jsonEn;
                break;
        }
      }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fav) {
            Msg msg = new Msg();
            msg.msg(this, "Coming Soon...");
            /*Intent intent = new Intent(this, Favorite.class);
            startActivity(intent);*/
        }  else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Quran Player");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.smodj.app.quranplayer \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Quran Player"));
            } catch (Exception e) {
                Msg msg = new Msg();
                msg.msg(this, "Failed");
            }

        } else if (id == R.id.nav_feedback) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.feedback, null);
            final EditText mEmail =  mView.findViewById(R.id.etEmail);
            final EditText mPassword = mView.findViewById(R.id.textArea);
            Button mLogin = mView.findViewById(R.id.btnSubmit);

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Msg m = new Msg();
                    if(!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()){

                       m.msg(MainActivity.this,"Feedback Sent!");
                        dialog.dismiss();
                    }else{
                        m.msg(MainActivity.this,"Please fill any empty fields");
                    }
                }
            });
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String loadJSONFromAsset() {
        StorageHelper sh = new StorageHelper(this);
        String result = sh.read(jsonFile);
        return result;
    }

}
