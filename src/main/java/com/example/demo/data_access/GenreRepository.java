package com.example.demo.data_access;

import com.example.demo.models.Genre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;


public class GenreRepository {

    private static final String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";

    private static Connection conn = null;


    /**
     * Get all genres
     *
     * @return all genres
     */
    public ArrayList<Genre> getAllGenres() {
        ArrayList<Genre> genres = new ArrayList<>();
        try {
            //Connect to DB
            conn = DriverManager.getConnection(URL);
            //System.out.println("Connection to SQLite has been established");
            //Make SQL Query
            PreparedStatement ps = conn.prepareStatement("SELECT GenreId, Name FROM Genre ");

            //Excute Query
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {

                genres.add(
                        new Genre(
                                resultSet.getInt("GenreId"),
                                resultSet.getString("Name")
                        )
                );
            }
        } catch (Exception e) {
            e.toString();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.toString();
            }
        }
        return genres;
    }


    /**
     * Get 5 random genres.
     *
     * @return 5 random genres.
     */
    public ArrayList<Genre> generateRandomGenres() {
        ArrayList<Genre> randomlyGeneratedList = new ArrayList<>();
        ArrayList<Genre> genres = new ArrayList<>(getAllGenres());
        Collections.shuffle(genres);

        for (int i = 0; i < 5; i++) {
            randomlyGeneratedList.add(genres.get(i));
        }
        return randomlyGeneratedList;
    }
}
