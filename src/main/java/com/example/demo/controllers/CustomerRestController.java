package com.example.demo.controllers;

import com.example.demo.data_access.CustomerRepository;
import com.example.demo.models.Customer;
import com.example.demo.models.CustomerPerCountry;
import com.example.demo.models.CustomersMostPopularGenre;
import com.example.demo.models.HighestSpenders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class CustomerRestController {

    private final CustomerRepository customerRepository = new CustomerRepository();


    /**
     * Read all customers.
     *
     * @return
     */

    @GetMapping("/api/customers")
    public ArrayList<Customer> getAllCustomers() {
        return customerRepository.selectAllCustomers();
    }


    /**
     * Read specific customer by id
     *
     * @param id - customer id
     * @return a specific customer information.
     */
    @GetMapping(value = "/api/customers/{id}")
    public Customer getSpecificCustomer(@PathVariable String id) {
        return customerRepository.selectCustomerById(id);
    }


    /**
     * Read specific customer by first- and lastname
     *
     * @param firstName- customer firstname
     * @param lastName   - customer lastname
     * @return returns that specific customer information.
     */
    @GetMapping("api/customers/customer")
    public Customer getCustomerByName(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        return customerRepository.selectCustomerByName(firstName, lastName);
    }


    /**
     * Read the customers in the database with limit and offset.
     *
     * @param limit  - limits the number of customers that are returned
     * @param offset - points out where to start.
     * @return returns a page customers from the database.
     */
    @RequestMapping(value = "/api/customers/customer-limit-and-offset")
    public ArrayList<Customer> getCustomerByLimitAndOffset(@RequestParam(value = "limit") int limit, @RequestParam(value = "offset") int offset) {
        return customerRepository.selectCustomersByOffsetAndLimit(limit, offset);
    }


    /**
     * Adds a new customer to the database.
     *
     * @param customer - the new customer we adding.
     */

    @RequestMapping(value = "/api/customer/", method = RequestMethod.POST)
    public boolean addCustomer(@RequestBody Customer customer) {
        return customerRepository.addCustomer(customer);
    }


    /**
     * Updates an existing customer
     *
     * @param id       -customer id
     * @param customer - Customer
     */
    @RequestMapping(value = "/api/customers/update-customer/{id}", method = RequestMethod.PUT)
    public void updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        customerRepository.updateCustomer(id, customer);
    }

    /**
     * Read customers per country.
     *
     * @return the number of customers in each country.
     */
    @RequestMapping(value = "/api/customers/customer-per-countries", method = RequestMethod.GET)
    public ArrayList<CustomerPerCountry> getCustomerByCountry() {
        return customerRepository.selectCustomersByCountry();
    }

    /**
     * Read customers who are the highest spenders.
     *
     * @return returns the total each customer spent
     */
    @RequestMapping(value = "/api/customers/highest-spenders", method = RequestMethod.GET)
    public ArrayList<HighestSpenders> getHighestSpenders() {
        return customerRepository.selectHighestSpenders();
    }

    /**
     * Read a given customers most popular genre
     *
     * @param id - customer id
     * @return - the firstname, lastname, genreType and GenreCount of that specific customer.
     */
    @RequestMapping(value = "/api/customers/most-popular-genre/{id}", method = RequestMethod.GET)
    public ArrayList<CustomersMostPopularGenre> getCustomersMostPopularGenre(@PathVariable String id) {
        return customerRepository.selectCustomersMostPopularGenre(id);
    }

}
