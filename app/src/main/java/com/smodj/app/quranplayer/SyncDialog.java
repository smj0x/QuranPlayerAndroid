package com.smodj.app.quranplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.CompletionService;

/**
 * Created by smj on 11/25/17.
 */

public class SyncDialog extends DialogPreference {

    public SyncDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialog_sync);


    }
    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setTitle(null);
        builder.setPositiveButton(null, null);
        builder.setNegativeButton(null, null);
        super.onPrepareDialogBuilder(builder);
    }
    @Override
    public void onBindDialogView(View view){
        super.onBindDialogView(view);

        webRequest(Constants.urlEn,0);
        webRequest(Constants.urlAr,1);
        //sh.write(Constants.jsonAr, Constants.temp);

    }

    private void webRequest(String URL, final int code){
        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                StorageHelper sh = new StorageHelper(getContext());
                String en = sh.read(Constants.jsonEn);
                String ar = sh.read(Constants.jsonAr);
                String temp = response.substring(12,response.length()-1);
                if (en.equals(temp) && ar.equals(temp) && code == 1) {
                    Msg.msg(getContext(),"Library is up to Date");
                    getDialog().dismiss();
                }
                else{
                    if(code == 0){
                        sh.write(Constants.jsonEn, temp);
                    }else{
                        sh.write(Constants.jsonAr, temp);
                        Msg.msg(getContext(),"Library Updated!");
                        getDialog().dismiss();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue  queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

}
