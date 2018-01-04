package com.example.huongnguyen.spotifyapp;

import java.util.ArrayList;

/**
 * Created by huongnguyen on 10/11/17.
 */

public class PlaylistTrack {
    String name;
    ArrayList<String> artists;
    String uri;

    public PlaylistTrack(String name, ArrayList<String> artists, String uri) {
        this.name = name;
        this.artists = artists;
        this.uri = uri;
    }
}
