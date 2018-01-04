package com.example.huongnguyen.spotifyapp;

public class Song {
    private String title;
    private String artist;
    private String uri;
    private String imgUrl;
    //private int imageId;

    Song(String songTitle, String songArtist, String songUri, String image) {
        title = songTitle;
        artist = songArtist;
        uri = songUri;
        imgUrl = image;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getUri() { return uri; }
    public String getImageId() { return imgUrl; }
}
