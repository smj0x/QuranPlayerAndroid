package com.smodj.app.quranplayer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by smj on 11/25/17.
 */

public class StorageHelper {
    private final String STORAGE = "com.smodj.app.quranplayer.STORAGE";
    private SharedPreferences preferences;
    private Context context;

    public StorageHelper(Context context) {
        this.context = context;
    }

    public void write(String key,String data){
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, data);
        editor.commit();
    }
    public String read(String key){
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        return preferences.getString(key,null);

    }
}