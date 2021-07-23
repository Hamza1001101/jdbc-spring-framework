package com.example.demo.data_access;

import com.example.demo.models.Customer;
import com.example.demo.models.CustomerPerCountry;
import com.example.demo.models.HighestSpenders;

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
            conn = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO Customer (CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, customer.getCustomerId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getCountry());
            preparedStatement.setString(5, customer.getPostalCode());
            preparedStatement.setString(6, customer.getPhone());
            preparedStatement.setString(7, customer.getEmail());

            int result = preparedStatement.executeUpdate();
            success = (result != 0); // if
            System.out.println("Add went well");
        } catch (Exception e) {
            System.out.println(e.getMessage());

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

            ps.setString(2, updateCustomer.getLastName() == null || updateCustomer.getLastName().isEmpty()
                    ? existingCustomer.getLastName() : updateCustomer.getLastName());

            ps.setString(3, updateCustomer.getCountry() == null || updateCustomer.getCountry().isEmpty()
                    ? existingCustomer.getCountry() : updateCustomer.getCountry());

            ps.setString(4, updateCustomer.getPostalCode() == null || updateCustomer.getPostalCode().isEmpty()
                    ? existingCustomer.getPostalCode() : updateCustomer.getPostalCode());

            ps.setString(5, updateCustomer.getPhone() == null || updateCustomer.getPhone().isEmpty()
                    ? existingCustomer.getPhone() : updateCustomer.getPhone());

            ps.setString(6, updateCustomer.getEmail() == null || updateCustomer.getEmail().isEmpty()
                    ? existingCustomer.getEmail() : updateCustomer.getEmail());

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

    public ArrayList<HighestSpenders> selectHighestSpenders() {
        ArrayList<HighestSpenders> highestSpenders = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement("SELECT C.CustomerId, C.FirstName, C.LastName, SUM(TOTAL) AS Total " +
                    "FROM Customer C " +
                    "JOIN Invoice I on C.CustomerId = I.CustomerId " +
                    "GROUP BY I.CustomerId " +
                    "ORDER BY Total DESC ");

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                highestSpenders.add(
                        new HighestSpenders(
                                resultSet.getString("CustomerId"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getInt("Total")
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
        return highestSpenders;
    }

    private static void log(String message) {
        DateFormat dateFormat = new SimpleDateFormat("\"yyyy/MM/dd HH:mm:ss\"");
        Date date = new Date();
        System.out.println(dateFormat.format(date) + " : " + message);
    }

}
