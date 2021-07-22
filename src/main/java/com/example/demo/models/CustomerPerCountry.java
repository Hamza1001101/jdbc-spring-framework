package com.example.demo.models;

public class CustomerPerCountry {
    private int Customers;
    private String Country;

    public CustomerPerCountry(int customers, String country) {
        this.Customers = customers;
        this.Country = country;
    }

    public int getCustomers() {
        return Customers;
    }

    public void setCustomers(int customers) {
        this.Customers = customers;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        this.Country = country;
    }
}
