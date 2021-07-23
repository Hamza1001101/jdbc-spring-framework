package com.example.demo.models;

public class Artist {
    private int ArtistId;
    private String Name;

    public Artist(int artistId, String name) {
        ArtistId = artistId;
        Name = name;
    }

    public int getArtistId() {
        return ArtistId;
    }

    public String getName() {
        return Name;
    }
}
