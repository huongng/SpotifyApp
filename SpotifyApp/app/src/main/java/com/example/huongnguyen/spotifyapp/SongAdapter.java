package com.example.huongnguyen.spotifyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;



public class SongAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<Song> list = Collections.emptyList();
    Context context;

    public interface OnItemClickListener {
        void onItemClick(Song item);
    }
    private final OnItemClickListener listener;

    public SongAdapter(List<Song> list, Context context, OnItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the layout - initialize the View Holder
        View v0 = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new MyViewHolder(v0);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Song item = list.get(position);
//        holder.title.setText(item.getTitle());
//        holder.artist.setText(item.getArtist());
//        holder.image.setImageResource(item.getImageId());
         holder.bind(context, list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
