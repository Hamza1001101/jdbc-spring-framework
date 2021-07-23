package com.example.demo.data_access;

import com.example.demo.models.Genre;
import com.example.demo.models.Track;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

public class TrackRepository {
    private static final String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";

    private static Connection conn = null;


    public ArrayList<Track> getAllTracks() {
        ArrayList<Track> tracks = new ArrayList<>();
        try {
            //Connect to DB
            conn = DriverManager.getConnection(URL);
            //System.out.println("Connection to SQLite has been established");
            //Make SQL Query
            PreparedStatement ps = conn.prepareStatement("SELECT TrackId, Name FROM Track ");

            //Excute Query
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {

                tracks.add(
                        new Track(
                                resultSet.getInt("TrackId"),
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
        return tracks;
    }


    public ArrayList<Track> getRandomTracks() {

        //  Collections.shuffle(genreRepository.getAllGenres());
        Random rand = new Random();
        Track randomElement;
        ArrayList<Track> te = new ArrayList<>();


        for (int i = 0; i < 5; i++) {

            int randomIndex = rand.nextInt(getAllTracks().size());
            randomElement = getAllTracks().get(randomIndex);
            getAllTracks().remove(randomIndex);
            te.add(randomElement);
        }

        return te;
    }
}
