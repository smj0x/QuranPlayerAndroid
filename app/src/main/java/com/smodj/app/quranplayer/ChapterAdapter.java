package com.smodj.app.quranplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by smj on 11/1/17.
 */

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private Context context;
    private Author data;


     ChapterAdapter(Context context, Author data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //creates view holder
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chapters_list, parent, false);
        return new ChapterAdapter.ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {
        final String key = data.get_Suras()[position];
        holder.chpName.setText(data.getChaptersValue(key));

        //handling onclick for an item or card
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Variables to pass to Player intent
                PlayerModel pm =new PlayerModel();
                pm.title = data.getChaptersValue(key);
                pm.AuthorNamre = data.getName();
                pm.chapterNumber=key;
                pm.url=data.getServer();


                //specified class
                IntentHelper.addObjectForKey(pm, "playerObject");
                Intent intent=new Intent(view.getContext(),Player.class);
                view.getContext().startActivity(intent);

            }});
*/

    }

    @Override
    public int getItemCount() {
        return Integer.parseInt(data.getCount());
    }

     class ChapterViewHolder extends RecyclerView.ViewHolder {

        TextView chpName;
        ChapterViewHolder(View itemView) {
            super(itemView);

            chpName = itemView.findViewById(R.id.chpName);

        }
    }

}
