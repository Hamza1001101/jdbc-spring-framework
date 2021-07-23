package com.example.demo.models;

public class Track {
    private final int TrackId;
    private final String Name;

    public Track(int trackId, String name) {
        TrackId = trackId;
        Name = name;
    }

    public int getTrackId() {
        return TrackId;
    }

    public String getName() {
        return Name;
    }
}
