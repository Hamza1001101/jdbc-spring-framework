package com.example.demo.models;

public class HighestSpenders {

    private String CustomerId;
    private String FirstName;
    private String LastName;
    private int Total;

    public HighestSpenders(String customerId, String firstName, String lastName, int total) {
        this.Total= total;
        this.FirstName= firstName;
        this.LastName=lastName;
        this.CustomerId=customerId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public int getTotal() {
        return Total;
    }
}
