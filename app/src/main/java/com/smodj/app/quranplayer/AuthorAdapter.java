package com.smodj.app.quranplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by smj on 10/31/17.
 */

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>{

    private Context context;
    private Author[] data;

    public AuthorAdapter(Context context, Author[] data) {
        this.context  = context;
        this.data = data;
    }

    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //creates view holder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.authors_list,parent,false);
        return new AuthorViewHolder(view);
        //return null;
    }

    @Override
    public void onBindViewHolder(AuthorViewHolder holder, int position) {
        //bind your data to view holder
        final Author auth = data[position];
        String name = auth.getName();
        String count = auth.getCount();
        holder.athrName.setText(name);
        holder.athrCount.setText(count);
        //handling onclick for an item or card
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Variables to pass to Chapters intent
                Author authPass = auth;
                authPass.set_Suras(auth.getSuras());

                //specified class
                IntentHelper.addObjectForKey(authPass, "authorObject");
                Intent intent=new Intent(view.getContext(),Chapter.class);
                view.getContext().startActivity(intent);

            }});

    }

    @Override
    public int getItemCount() {
        //this will return no of data to get the no of cards on display
        return data.length;
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder{

        TextView athrName,athrCount;
        public AuthorViewHolder(View itemView) {
            super(itemView);
            //CardView cardView = itemView.findViewById(R.id.card_view);
            //cardView.setCardElevation(2);
            //cardView.setPadding(8,8,8,8);

            athrName = itemView.findViewById(R.id.authorName);
            athrCount = itemView.findViewById(R.id.authorNoOfChapters);
        }
    }

}
