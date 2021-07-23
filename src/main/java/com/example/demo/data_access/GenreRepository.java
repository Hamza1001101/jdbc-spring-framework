package com.example.demo.data_access;

import com.example.demo.models.Customer;
import com.example.demo.models.Genre;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class GenreRepository {

    private static final String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";

    private static Connection conn = null;


    public Genre selectGenreById(int id) {
        Genre genres = null;
        try {
            //Connect to DB
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established");
            //Make SQL Query
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT  GenreId, Name FROM Genre " +
                            "WHERE  GenreId = ? ");

            ps.setInt(1, id);
            //Excute Query
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                genres = new Genre(
                        resultSet.getInt("GenreId"),
                        resultSet.getString("Name")
                );
            }
            System.out.println("Selected all customers successfully");

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return genres;
    }

    public int getMaxGenreId() {
        int maxGenreId = 0;
        try {
            //Connect to DB
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established");
            //Make SQL Query
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT  MAX(GenreId) AS GenreId FROM Genre ");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                maxGenreId = resultSet.getInt("GenreId");
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return maxGenreId;
    }




    public ArrayList<Genre> getRandomGenres() {
        ArrayList<Genre> genres = new ArrayList<>();
        Random random = new Random();
        int maxGenreId = getMaxGenreId();
        ArrayList<Integer> previousRandomNumbers = new ArrayList<>();


        int i = 0;
        while (i < 5) {
            // Check if this random statement is correct
            int randomNumber = random.nextInt(getMaxGenreId()) + 1;

            while (previousRandomNumbers.contains(randomNumber)) {
                randomNumber = random.nextInt(getMaxGenreId()) + 1;
            }

//            System.out.println(randomNumber);

            genres.add(selectGenreById(randomNumber));

            previousRandomNumbers.add(randomNumber);
            i++;
        }


        return genres;

    }
}
