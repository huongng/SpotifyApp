package com.example.huongnguyen.spotifyapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by huongnguyen on 10/9/17.
 */

public class PlaySongVH extends RecyclerView.ViewHolder  {

    CardView cv;
    TextView title;
    TextView artist;
    ImageView image;
    Button playBtn, prevBtn, nextBtn;
    public PlaySongVH(View itemView) {
        super(itemView);
        cv = itemView.findViewById(R.id.cardView_playsong);
        title = itemView.findViewById(R.id.title);
        artist = itemView.findViewById(R.id.artist);
        image = itemView.findViewById(R.id.song_cover);
        playBtn = itemView.findViewById(R.id.playpause);
        prevBtn = itemView.findViewById(R.id.prev);
        nextBtn = itemView.findViewById(R.id.next);
    }
}
