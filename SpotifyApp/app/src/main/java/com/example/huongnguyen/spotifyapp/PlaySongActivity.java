package com.example.huongnguyen.spotifyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Metadata;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huongnguyen on 10/9/17.
 */

public class PlaySongActivity extends AppCompatActivity implements
        Player.NotificationCallback, ConnectionStateCallback {

    public static final String TAG = "PlaySongActivity";

    @SuppressWarnings("SpellCheckingInspection")
    private static final String CLIENT_ID = "cf4e2e814e1648c4b600533e57a4d77c";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String REDIRECT_URI = "huongnguyen-login://callback";
    private static final int REQUEST_CODE = 1337;

    private SpotifyPlayer mPlayer;

    private String songUri;
    private String artist;
    private String title;
    private String accessToken; // <<<
    private int imageId;
    private String imgUrl; // <<<

    private String[] songStringInfo;
    private Metadata mMetadata;
    private AuthenticationResponse response;

    private final Player.OperationCallback mOperationCallback = new Player.OperationCallback() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "OK");
        }

        @Override
        public void onError(Error error) {
            Log.d(TAG, "Error");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playsong);
        //login();

        // Get the intent that starts this activity
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        //imageId = b.getInt(AmbientActivity.EXTRA_MESSAGE_IMAGE);
        imgUrl = b.getString(AmbientActivity.EXTRA_MESSAGE_IMAGE);
        songStringInfo = b.getStringArray(AmbientActivity.EXTRA_MESSAGE_STRING);

        songUri = songStringInfo[0];
        title = songStringInfo[1];
        artist = songStringInfo[2];
        accessToken = songStringInfo[3]; // <<<

        configPlayer(); // <<<
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.playsong_view);
        PlaySongAdapter adapter = new PlaySongAdapter(
                getApplicationContext(),
                songUri, title, artist, imgUrl,
                new PlaySongAdapter.AdapterCallback() {

                    @Override
                    public void onClickAtPlayButton(int position) {
                        onPlayButtonClicked();
                    }
                });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<Song> getSong() {
        List<Song> song = new ArrayList<>();

        song.add(new Song(title, artist, songUri, imgUrl));

        return song;
    }

    private void configPlayer() {
        Config playerConfig = new Config(this, accessToken, CLIENT_ID);
        Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
            @Override
            public void onInitialized(SpotifyPlayer spotifyPlayer) {
                mPlayer = spotifyPlayer;
                mPlayer.addConnectionStateCallback(PlaySongActivity.this);
                mPlayer.addNotificationCallback(PlaySongActivity.this);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });
    }
//    private void login() {
//        final AuthenticationRequest request = new AuthenticationRequest.Builder(
//                CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
//                .setScopes(new String[]{"user-read-private",
//                        "playlist-read", "playlist-read-private", "streaming"})
//                .build();
//
//        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//
//        // Check if result comes from the correct activity
//        if (requestCode == REQUEST_CODE) {
//            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
//            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
//                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
//                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
//                    @Override
//                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
//                        mPlayer = spotifyPlayer;
//                        mPlayer.addConnectionStateCallback(PlaySongActivity.this);
//                        mPlayer.addNotificationCallback(PlaySongActivity.this);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
//                    }
//                });
//            }
//        }
//    }
    @Override
    public void onLoggedIn() {
        mPlayer.playUri(null, songUri, 0, 0);
    }

    public void onPlayButtonClicked() {
        mPlayer.pause(mOperationCallback);
    }

    @Override
    public void onLoggedOut() {

    }


    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
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
