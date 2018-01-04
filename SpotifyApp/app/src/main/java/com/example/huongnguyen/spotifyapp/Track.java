package com.example.huongnguyen.spotifyapp;

/**
 * Created by huongnguyen on 10/11/17.
 */

public class Track {
    public String name;
    public String artist;
    public String imgUrl;
    public String uri;

    public Track(String name, String artist, String imgUrl, String uri) {
        this.name = name;
        this.artist = artist;
        this.imgUrl = imgUrl;
        this.uri = uri;
    }

}
