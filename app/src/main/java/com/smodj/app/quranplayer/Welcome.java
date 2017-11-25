package com.smodj.app.quranplayer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by smj on 11/7/17.
 */

public class Welcome extends AppCompatActivity {
    LinearLayout linearLayout;
    Animation animate;
    private final int WAIT_TIME = 850;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Main View
        setContentView(R.layout.welcome);
        linearLayout = findViewById(R.id.welcomeIcon);
        animate = AnimationUtils.loadAnimation(this, R.anim.dropdown);
        linearLayout.setAnimation(animate);

        new PrefetchData().execute();

    }

    /**
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            /*
             * Will make http call here This call will download required data
             * before launching the app
             * example:
             * 1. Downloading and storing in SQLite
             * 2. Downloading images
             * 3. Fetching and parsing the xml / json
             * 4. Sending device information to server
             * 5. etc.,
             */


            //Checking if Json File from Assets is Loaded or Not Status
            StorageHelper sh  = new StorageHelper(Welcome.this);

            if (sh.read("firstTime")==null){
                sh.write("firstTime","notAnymore");
                sh.write(Constants.jsonEn,loadJSONFromAsset(Constants.jsonEn));
                sh.write(Constants.jsonAr,loadJSONFromAsset(Constants.jsonAr));
            }
            /*JsonParser jsonParser = new JsonParser();
            String json = jsonParser
                    .getJSONFromUrl("https://api.androidhive.info/game/game_stats.json");

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jObj = new JSONObject(json)
                            .getJSONObject("game_stat");
                    now_playing = jObj.getString("now_playing");
                    earned = jObj.getString("earned");

                    Log.e("JSON", "> " + now_playing + earned);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }*/

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            // will close this activity and lauch main activity
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(WAIT_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        Intent i = new Intent(Welcome.this, MainActivity.class);
                        startActivity(i);

                        // close this activity
                        finish();
                    }

                }
            };
            timer.start();

        }
        public String loadJSONFromAsset(String jsonFile) {
            String json = null;
            try {
                InputStream is = getAssets().open(jsonFile);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return json;
        }
    }
}
