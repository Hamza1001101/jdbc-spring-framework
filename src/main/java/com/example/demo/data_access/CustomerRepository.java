package com.example.demo.data_access;

import com.example.demo.models.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomerRepository {
    private static final String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private static ArrayList<Customer> customers = new ArrayList<Customer>();
    private static Connection conn = null;

    public ArrayList<Customer> selectAllCustomers() {

        try {
            //Connect to DB
            conn = DriverManager.getConnection(URL);
            log("Connection to SQLite has been established");
            //Make SQL Query
            PreparedStatement ps = conn.prepareStatement("SELECT  CustomerId, FirstName, LastName, Email,PostalCode, Phone, Country FROM Customer");

            //Excute Query
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                resultSet.getString("CustomerId"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Country"),
                                resultSet.getString("PostalCode"),
                                resultSet.getString("Phone"),
                                resultSet.getString("Email")
                        )
                );
            }
            log("Selected all customers successfully");

        } catch (Exception e) {
            log(e.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                log(e.toString());
            }
        }
        return customers;
    }

    public Customer selectCustomerById(String customerId) {
        Customer customer = null;
        try {
            //Connect to DB
            conn = DriverManager.getConnection(URL);
            log("Connection to SQLite has been established");

            //Make SQL Query
            PreparedStatement ps = conn.prepareStatement("SELECT  CustomerId, FirstName, LastName, Email,PostalCode, Phone, Country FROM Customer WHERE CustomerId= ?");

            ps.setString(1, customerId);

            //Excute Query
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {

                customer = new Customer(
                        resultSet.getString("CustomerId"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Country"),
                        resultSet.getString("PostalCode"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email")

                );
            }
            log("Selected all customers successfully");


        } catch (Exception e) {
            log(e.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                log(e.toString());
            }
        }
        return customer;
    }

    private static void log(String message) {
        DateFormat dateFormat = new SimpleDateFormat("\"yyyy/MM/dd HH:mm:ss\"");
        Date date = new Date();
        System.out.println(dateFormat.format(date) + " : " + message);
    }

}
