package com.smodj.app.quranplayer;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by smj on 11/3/17.
 */

public class Msg {

    public static void msg(Context con, String str)
    {
        Toast.makeText(con, str, Toast.LENGTH_LONG).show();
    }


}
