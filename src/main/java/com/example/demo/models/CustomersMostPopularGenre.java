package com.example.demo.models;

public class CustomersMostPopularGenre {

    private String firstName;
    private String lastName;
    private String genreType;
    private int genreCount;

    /**
     * @param firstName  - customer firstname
     * @param lastName   - customer lastname
     * @param genreType  - customer genre type
     * @param genreCount - customer genre count
     */
    public CustomersMostPopularGenre(String firstName, String lastName, String genreType, int genreCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.genreCount = genreCount;
        this.genreType = genreType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGenreType() {
        return genreType;
    }

    public int getGenreCount() {
        return genreCount;
    }
}
