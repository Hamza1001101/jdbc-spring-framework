package com.example.demo.models;

public class Genre {
    private int GenreId;
    private String Name;

    public Genre(int genreId, String name) {
        GenreId = genreId;
        Name = name;
    }

    public int getGenreId() {
        return GenreId;
    }

    public void setGenreId(int genreId) {
        GenreId = genreId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
