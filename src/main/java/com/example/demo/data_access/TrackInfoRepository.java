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


    public ArrayList<TrackInfo> getTrackInfo(String searchTerm) {
        ArrayList<TrackInfo> trackInfoArrayList = new ArrayList<>();
        try {
            //Connect to DB
            conn = DriverManager.getConnection(URL);

            PreparedStatement ps = conn.prepareStatement("SELECT tr.TrackId, tr.Name AS Track, ar.Name AS Artist, al.Title as Album, " +
                    " ge.Name AS Genre " +
                    " FROM Track tr " +
                    " INNER JOIN Album al ON tr.AlbumId = al.AlbumId " +
                    " INNER JOIN Artist ar ON al.ArtistId= ar.ArtistId " +
                    " INNER JOIN Genre ge ON tr.GenreId= ge.GenreId " +
                    " WHERE tr.Name LIKE ?");

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
