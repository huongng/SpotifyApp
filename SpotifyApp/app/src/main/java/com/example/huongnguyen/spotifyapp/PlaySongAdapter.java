package com.example.huongnguyen.spotifyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.bumptech.glide.Glide;


import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

/**
 * Created by huongnguyen on 10/9/17.
 */

public class PlaySongAdapter extends RecyclerView.Adapter<PlaySongVH> implements
        Player.NotificationCallback, ConnectionStateCallback {

    public static final String TAG = "PlaySongAdapter";

    @SuppressWarnings("SpellCheckingInspection")
    private static final String CLIENT_ID = "cf4e2e814e1648c4b600533e57a4d77c";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String REDIRECT_URI = "huongnguyen-login://callback";
    private static final int REQUEST_CODE = 1337;

    private SpotifyPlayer mPlayer;

    private Player.OperationCallback mOperationCallback;

    Context context;
    String uri, title, artist;
    String imageId;
    private AdapterCallback mAdapterCallback;

    public interface AdapterCallback {
        void onClickAtPlayButton(int position);
    }

    public PlaySongAdapter(Context context, String uri, String title,
                           String artist, String imageId,
                           AdapterCallback mAdapterCallback) {
        this.context = context;
        this.uri = uri;
        this.title = title;
        this.artist = artist;
        this.imageId = imageId;
        this.mAdapterCallback = mAdapterCallback;
    }

    @Override
    public PlaySongVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v0 = LayoutInflater.from(parent.getContext()).inflate(R.layout.playsong_item, parent, false);
        return new PlaySongVH(v0);
    }

    @Override
    public void onBindViewHolder(PlaySongVH holder, final int position) {
        holder.title.setText(title);
        holder.artist.setText(artist);
        //holder.image.setImageResource(imageId);
        Glide.with(context).load(imageId).into(holder.image);
        holder.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onClickAtPlayButton(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Error error) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {

    }

    @Override
    public void onPlaybackError(Error error) {

    }

}
