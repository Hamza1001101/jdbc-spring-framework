package com.example.demo.data_access;

import com.example.demo.models.TrackInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TrackInfoRepository {

    private static final String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";

    private static Connection conn = null;


    /**
     * Gets track info
     *
     * @param searchTerm - search Term
     * @return everything that matches the search term.
     */
    public ArrayList<TrackInfo> getTrackInfo(String searchTerm) {
        ArrayList<TrackInfo> trackInfoArrayList = new ArrayList<>();
        try {
            //Connect to DB
            conn = DriverManager.getConnection(URL);

            PreparedStatement ps = conn.prepareStatement("SELECT Tr.TrackId, Tr.Name AS Track, Ar.Name AS Artist, Al.Title as Album, " +
                    " Ge.Name AS Genre " +
                    " FROM Track Tr " +
                    " INNER JOIN Album Al ON Tr.AlbumId = Al.AlbumId " +
                    " INNER JOIN Artist Ar ON Al.ArtistId= Ar.ArtistId " +
                    " INNER JOIN Genre Ge ON Tr.GenreId= Ge.GenreId " +
                    " WHERE Tr.Name LIKE ?");

            ps.setString(1, "%" + searchTerm + "%");

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                trackInfoArrayList.add(
                        new TrackInfo(
                                resultSet.getInt("TrackId"),
                                resultSet.getString("Track"),
                                resultSet.getString("Artist"),
                                resultSet.getString("Album"),
                                resultSet.getString("Genre")
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
        return trackInfoArrayList;
    }
}
