package com.example.huongnguyen.spotifyapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


public class DinnerActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_STRING = "com.example.huongnguyen.spotifyapp.STRING";
    public static final String EXTRA_MESSAGE_IMAGE = "com.example.huongnguyen.spotifyapp.IMAGE";

    public static final String TAG = "AmbientPlaySongActivity";

    @SuppressWarnings("SpellCheckingInspection")
    private static final String CLIENT_ID = "cf4e2e814e1648c4b600533e57a4d77c";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String REDIRECT_URI = "huongnguyen-login://callback";
    private static final int REQUEST_CODE = 1337;

    //private SpotifyPlayer mPlayer;
    private AuthenticationResponse response;
    private String accessToken;

    @SuppressWarnings("SpellCheckingInspection")
    private String url = "https://api.spotify.com/v1/users/spotify/playlists/59ZbFPES4DQwEjBpWHzrtC";

    private Event playlist;
    private RecyclerView recyclerView;
    private SongAdapter adapter;

    List<Song>mSong = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient);

        //login(); // <<<

        Intent intent = getIntent();
        accessToken = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_STRING);

        PlaylistAsyncTask task = new PlaylistAsyncTask();
        task.execute(url, accessToken);
//
//        final List<Song> mSong = getSong(accessToken);
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ambient_list);
//
//        SongAdapter adapter = new SongAdapter(mSong, getApplication(),
//                new SongAdapter.OnItemClickListener() {
//                    @Override public void onItemClick(Song item) {
//                        Intent playSongIntent = new Intent(AmbientActivity.this, PlaySongActivity.class);
//                        String uri = item.getUri();
//                        String title = item.getTitle();
//                        String artist = item.getArtist();
//                        String accessToken = response.getAccessToken();
//                        int imageId = item.getImageId();
//
//                        playSongIntent.putExtra(EXTRA_MESSAGE_STRING, new String[] {uri, title, artist, accessToken});
//                        playSongIntent.putExtra(EXTRA_MESSAGE_IMAGE, imageId);
//                        startActivity(playSongIntent);
//                    }
//                });
//
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void login() {
        final AuthenticationRequest request = new AuthenticationRequest.Builder(
                CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
                .setScopes(new String[]{"user-read-private",
                        "playlist-read", "playlist-read-private", "streaming"})
                .build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Log.d(TAG, "accessToken " + response.getAccessToken());
            }
        }
    }

    private class PlaylistAsyncTask extends AsyncTask<String, Void, Event> {

        @Override
        protected Event doInBackground(String... strings) {
            return Utils.fetchPlaylistData(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Event event) {
            //Toast.makeText(AmbientActivity.this, "" + event.name, Toast.LENGTH_SHORT).show();
            playlist = event;

            // Http Request for Song List
            for (int i = 0; i < playlist.getSize(); i++) {
                String trackUri = playlist.getTrack(i).uri;
                String trackUrl = "https://api.spotify.com/v1/tracks/" + trackUri;
                SongListAsyncTask songListAsyncTask = new SongListAsyncTask();
                songListAsyncTask.execute(trackUrl, accessToken);
            }
        }
    }

    private class SongListAsyncTask extends AsyncTask<String, Void, Track> {

        @Override
        protected Track doInBackground(String... strings) {
            return Utils.fetchTrackData(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Track playlistTrack) {
            //final List<Song> mSong = getSong(playlistTrack);
            mSong.add(new Song(playlistTrack.name, playlistTrack.artist,
                    playlistTrack.uri, playlistTrack.imgUrl));
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ambient_list);

            SongAdapter adapter = new SongAdapter(mSong, getApplication(),
                    new SongAdapter.OnItemClickListener() {
                        @Override public void onItemClick(Song item) {
                            Intent playSongIntent = new Intent(DinnerActivity.this, PlaySongActivity.class);
                            String uri = item.getUri();
                            String title = item.getTitle();
                            String artist = item.getArtist();
                            //String accessToken = response.getAccessToken();
                            //int imageId = item.getImageId();
                            String imgUrl = item.getImageId();

                            playSongIntent.putExtra(EXTRA_MESSAGE_STRING, new String[] {uri, title, artist, accessToken});
                            playSongIntent.putExtra(EXTRA_MESSAGE_IMAGE, imgUrl);
                            startActivity(playSongIntent);
                        }
                    });

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(DinnerActivity.this));
        }
    }
    public List<Song> getSong(Track track) {
        List<Song> song = new ArrayList<>();

//        song.add(new Song(track.name, track.artist, track.uri, R.drawable.imyours));
//
//        song.add(new Song("I'm yours", "Jason Mraz", "spotify:track:2TpxZ7JUBn3uw46aR7qd6V",
//                R.drawable.imyours));
//        song.add(new Song("Yellow", "Coldplay", "spotify:track:6KywfgRqvgvfJc3JRwaZdZ",
//                R.drawable.coldplay));
//        song.add(new Song("Everything", "Michael Buble", "spotify:track:6KywfgRqvgvfJc3JRwaZdZ",
//                R.drawable.buble));
//        song.add(new Song("You and your heart", "Jack Johnson", "spotify:track:6KywfgRqvgvfJc3JRwaZdZ",
//                R.drawable.jack));
//        song.add(new Song("No Surprises", "Radiohead", "spotify:track:6KywfgRqvgvfJc3JRwaZdZ",
//                R.drawable.radiohead));
//        song.add(new Song("Look After You", "The Fray", "spotify:track:6KywfgRqvgvfJc3JRwaZdZ",
//                R.drawable.fray));
//        song.add(new Song("Street Map", "Athlete", "spotify:track:6KywfgRqvgvfJc3JRwaZdZ",
//                R.drawable.athelete));
//        song.add(new Song("Come Away with Me", "Norah Jones", "spotify:track:6KywfgRqvgvfJc3JRwaZdZ",
//                R.drawable.norah));
//        song.add(new Song("Riptide", "Vance Joy", "spotify:track:6KywfgRqvgvfJc3JRwaZdZ",
//                R.drawable.vance));
//        song.add(new Song("Love", "Lana Del Rey", "spotify:track:6KywfgRqvgvfJc3JRwaZdZ",
//                R.drawable.lana));

        return song;
    }

}