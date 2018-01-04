package com.example.huongnguyen.spotifyapp;

import java.util.ArrayList;

/**
 * Created by huongnguyen on 10/10/17.
 */

public class Event {
    public final ArrayList<PlaylistTrack> tracks;

    public Event(ArrayList<PlaylistTrack> tracks) {
        this.tracks = tracks;
    }

    public int getSize() {
        return this.tracks.size();
    }

    public PlaylistTrack getTrack(int index) {
        return tracks.get(index);
    }
}
