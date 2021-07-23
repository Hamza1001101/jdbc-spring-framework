package com.example.demo.models;

public class TrackInfo {
    private final int TrackId;
    private final String TrackName;
    private final String ArtistName;
    private final String AlbumTitle;
    private final String Genre;

    public TrackInfo(int trackId, String trackName, String artistName, String albumTitle, String genre) {
        TrackId = trackId;
        TrackName = trackName;
        ArtistName = artistName;
        AlbumTitle = albumTitle;
        Genre = genre;
    }

    public int getTrackId() {
        return TrackId;
    }

    public String getTrackName() {
        return TrackName;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public String getAlbumTitle() {
        return AlbumTitle;
    }

    public String getGenre() {
        return Genre;
    }
}
