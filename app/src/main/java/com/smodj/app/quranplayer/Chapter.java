package com.smodj.app.quranplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by smj on 11/1/2017.
 */

public class Chapter extends AppCompatActivity{

    String language;

    public static final String Broadcast_PLAY_NEW_AUDIO = "com.smodj.app.quranplayer.PlayNewAudio";

    private MediaPlayerService player;
    boolean serviceBound = false;
    ArrayList<Audio> audioList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Main View
        setContentView(R.layout.chapters);
        Author auth = (Author) IntentHelper.getObjectForKey("authorObject");
        //getSupportActionBar().setTitle(auth.getName());
        setActionBar(auth.getName());

        //Getting Setting values
        KeyValueHelper key = new KeyValueHelper();
        language = key.getPref("listLang",this);

        //setting dictionary from json based on selected authors chapters
        String dump = loadJSONFromAsset();
        //ChapterModel[] chaptersRefrenceList = new Gson().fromJson(dump, ChapterModel[].class);
        ChapterModelEnAr[] chaptersRefrenceListEnAr = new Gson().fromJson(dump, ChapterModelEnAr[].class);
        //String[] selectedChapters = auth.get_Suras();
        String chapterName=null;
        for (int i = 0 ; i < chaptersRefrenceListEnAr.length ; i++)
        {
            if(language == null){
                chapterName = chaptersRefrenceListEnAr[i].getNameEn();
            }
            else {
                switch (language) {
                    case "English":

                        chapterName = chaptersRefrenceListEnAr[i].getNameEn();
                        break;
                    case "Arabic":
                        chapterName = chaptersRefrenceListEnAr[i].getNameAr();
                        break;
                    default:
                        chapterName = chaptersRefrenceListEnAr[i].getNameEn();
                        break;
                }
            }

            auth.setChapters(chaptersRefrenceListEnAr[i].getNumber(),chapterName);
        }
        loadAudio(auth);
        //Recycler View
        initRecyclerView(auth);

    }
    private void loadAudio(Author auth) {
        for(int i=0; i<Integer.parseInt(auth.getCount());i++){
            String key = auth.get_Suras()[i];
            String title = auth.getChaptersValue(key);
            String artist = auth.getName();
            String album = title;
            String currentFile = String.format("%03d", Integer.parseInt(key));
            String data = auth.getServer()+"/"+currentFile+".mp3";
            audioList.add(new Audio(data, title, album, artist));
        }
    }
    //Recycler View
    private void initRecyclerView(Author auth) {
            RecyclerView chpLisRV = findViewById(R.id.chaptersListRV);
            //RecyclerView_Adapter adapter = new RecyclerView_Adapter(audioList, getApplication());
            chpLisRV.setAdapter(new ChapterAdapter(Chapter.this,auth));
            //chpLisRV.setAdapter(adapter);
            chpLisRV.setLayoutManager(new LinearLayoutManager(this));
            chpLisRV.addOnItemTouchListener(new CustomTouchListener(this, new onItemClickListener() {
                @Override
                public void onClick(View view, int index) {
                    playAudio(index);
                }
            }));
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("serviceStatus", serviceBound);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("serviceStatus");
    }

    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    private void playAudio(int audioIndex) {
        //Check is service is active
        if (!serviceBound) {
            //Store Serializable audioList to SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            storage.storeAudio(audioList);
            storage.storeAudioIndex(audioIndex);

            Intent playerIntent = new Intent(this, MediaPlayerService.class);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Store the new audioIndex to SharedPreferences
            StorageUtil storage = new StorageUtil(getApplicationContext());
            storage.storeAudioIndex(audioIndex);

            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            sendBroadcast(broadcastIntent);
        }
    }

    // Action Bar
    public void setActionBar(String heading) {
        // TODO Auto-generated method stub
        String _heading = heading.split("\\(")[0];
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle(_heading);
        actionBar.show();

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open(Constants.chapters);
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
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }

}
