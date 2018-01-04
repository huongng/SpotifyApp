package com.example.huongnguyen.spotifyapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
public class MyViewHolder extends RecyclerView.ViewHolder {
    CardView cv;
    TextView title;
    TextView artist;
    ImageView image;

    MyViewHolder(View view) {
        super(view);
        cv = view.findViewById(R.id.cardView);
        title = view.findViewById(R.id.song_title);
        artist = view.findViewById(R.id.song_artist);
        image = view.findViewById(R.id.image);
    }


    public void bind(Context context, final Song item, final SongAdapter.OnItemClickListener listener) {
        title.setText(item.getTitle());
        artist.setText(item.getArtist());
        //image.setImageResource(item.getImageId());
        Glide.with(context).load(item.getImageId())
                .into(image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(item);
            }
        });
    }
}