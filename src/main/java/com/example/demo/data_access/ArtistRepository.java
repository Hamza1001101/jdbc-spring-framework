package com.example.demo.data_access;

import com.example.demo.models.Artist;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

public class ArtistRepository {
    private static final String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";

    private static Connection conn = null;


    /**
     * Returns all artists
     *
     * @return All artists
     */
    public ArrayList<Artist> getAllArtists() {
        ArrayList<Artist> artists = new ArrayList<>();
        try {
            //Connect to DB
            conn = DriverManager.getConnection(URL);
            //System.out.println("Connection to SQLite has been established");
            //Make SQL Query
            PreparedStatement ps = conn.prepareStatement("SELECT ArtistId, Name FROM Artist ");

            //Excute Query
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {

                artists.add(
                        new Artist(
                                resultSet.getInt("ArtistId"),
                                resultSet.getString("Name")
                        )
                );
            }
            //System.out.println("Selected all customers successfully");

        } catch (Exception e) {
            e.toString();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.toString();
            }
        }
        return artists;
    }


    /**
     * Get 5 random artis.
     *
     * @return 5 random artists
     */
    public ArrayList<Artist> getRandomArtists() {

        Random rand = new Random();
        Artist randomElement;
        ArrayList<Artist> unique = new ArrayList<>();


        for (int i = 0; i < 5; i++) {

            int randomIndex = rand.nextInt(getAllArtists().size());
            randomElement = getAllArtists().get(randomIndex);
            getAllArtists().remove(randomIndex);
            unique.add(randomElement);
        }

        return unique;
    }
}
