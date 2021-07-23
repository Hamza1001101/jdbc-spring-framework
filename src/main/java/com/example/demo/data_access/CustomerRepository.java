package com.example.demo.data_access;

import com.example.demo.models.Customer;
import com.example.demo.models.CustomerPerCountry;

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

    private static Connection conn = null;
    ArrayList<Customer> customers = new ArrayList<>();

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

    public Customer selectCustomerByName(String firstName, String lastName) {
        Customer customer = null;
        try {
            //Connect to DB
            conn = DriverManager.getConnection(URL);
            log("Connection to SQLite has been established");

            //Make SQL Query
            PreparedStatement ps = conn.prepareStatement("SELECT  CustomerId, FirstName, LastName, Email,PostalCode, Phone, Country " +
                    "FROM Customer " +
                    "WHERE FirstName LIKE ? AND LastName LIKE ? ");

            ps.setString(1, "%" + firstName + "%");
            ps.setString(2, "%" + lastName + "%");

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

    public ArrayList<Customer> selectCustomersByOffsetAndLimit(int limit, int offset) {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(" SELECT CustomerId, FirstName, LastName, Email,PostalCode, Phone, Country " +
                    "FROM Customer LIMIT ? OFFSET ?");
            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                "",
                                "",
                                resultSet.getString("LastName"),
                                resultSet.getString("Country"),
                                resultSet.getString("PostalCode"),
                                resultSet.getString("Phone"),
                                resultSet.getString("Email")
                        )
                );
                log("selectCustomerByOffsetAndLimit executed successfully!");
            }
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

    public Boolean addCustomer(Customer customer) {
        boolean success = false;
        try {
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("INSERT INTO Customer(CustomerId, FirstName, LastName, Email,PostalCode, Phone, Country)" +
                            " VALUES(?,?,?,?,?,?, ?)");
            prep.setString(1, customer.getCustomerId());
            prep.setString(2, customer.getFirstName());
            prep.setString(3, customer.getLastName());
            prep.setString(4, customer.getCountry());
            prep.setString(5, customer.getPostalCode());
            prep.setString(6, customer.getPhone());
            prep.setString(7, customer.getEmail());
            int result = prep.executeUpdate();
            success = (result != 0); // if res = 1; true

            System.out.println("Add went well!");

        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        // ---
        return success;
    }


    public ArrayList<CustomerPerCountry> selectCustomersByCountry() {
        ArrayList<CustomerPerCountry> customersByCountry = new ArrayList<>();
        try {
            //Connect to DB
            conn = DriverManager.getConnection(URL);
            log("Connection to SQLite has been established");
            //Make SQL Query
            PreparedStatement ps = conn.prepareStatement("SELECT  COUNT(CustomerId) AS Customers, Country " +
                    "FROM Customer " +
                    "GROUP BY Country " +
                    "ORDER BY COUNT(CustomerId) DESC ");

            //Excute Query
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                customersByCountry.add(
                        new CustomerPerCountry(
                                resultSet.getInt("Customers"),
                                resultSet.getString("Country")
                        )
                );
            }
            log("SelectCustomersByCountry run successfully");

        } catch (Exception e) {
            log(e.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                log(exception.toString());
            }
        }
        return customersByCountry;
    }

    public void updateCustomer(String id, Customer updateCustomer) {
        Customer existingCustomer = selectCustomerById(id);

        try {
            conn = DriverManager.getConnection(URL);
            log("Connection to Database has been established");

            // Make SQL Query
            PreparedStatement ps = conn.prepareStatement("UPDATE Customer " +
                    "SET FirstName=?, LastName=?, Country=?, PostalCode=?, Phone=?, Email=? " +
                    "WHERE CustomerId= ?");

            ps.setString(1, updateCustomer.getFirstName() == null || updateCustomer.getFirstName().isEmpty()
                    ? existingCustomer.getFirstName() : updateCustomer.getFirstName());

            if (updateCustomer.getLastName() == null || updateCustomer.getLastName().isEmpty()) {
                ps.setString(2, existingCustomer.getLastName());
            } else {
                ps.setString(2, updateCustomer.getLastName());
            }

            if (updateCustomer.getCountry() == null || updateCustomer.getCountry().isEmpty()) {
                ps.setString(3, existingCustomer.getCountry());
            } else {
                ps.setString(3, updateCustomer.getCountry());
            }

            if (updateCustomer.getPostalCode() == null || updateCustomer.getPostalCode().isEmpty()) {
                ps.setString(4, existingCustomer.getPostalCode());
            } else {
                ps.setString(4, updateCustomer.getPostalCode());
            }

            if (updateCustomer.getPhone() == null || updateCustomer.getPhone().isEmpty()) {
                ps.setString(5, existingCustomer.getPhone());
            } else {
                ps.setString(5, updateCustomer.getPhone());
            }

            if (updateCustomer.getEmail() == null || updateCustomer.getEmail().isEmpty()) {
                ps.setString(6, existingCustomer.getEmail());
            } else {
                ps.setString(6, updateCustomer.getEmail());
            }

            ps.setString(7, id);
            ps.executeUpdate();
            System.out.println("Successfully updated!");
        } catch (Exception e) {
            log(e.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                log(e.toString());
            }
        }
    }

    private static void log(String message) {
        DateFormat dateFormat = new SimpleDateFormat("\"yyyy/MM/dd HH:mm:ss\"");
        Date date = new Date();
        System.out.println(dateFormat.format(date) + " : " + message);
    }

}
