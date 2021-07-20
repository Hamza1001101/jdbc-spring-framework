package com.example.demo.controllers;

import com.example.demo.data_access.CustomerRepository;
import com.example.demo.models.CustomerDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


/*@RestController
public class CustomerController {
    private final CustomerRepository customerRepository = new CustomerRepository();

    @RequestMapping(value="/api/customers", method= RequestMethod.GET)
    public ArrayList<CustomerDetails> selectAllCustomers() {
        return customerRepository.selectAllCustomers();
    }
}
/*/

@RestController
public class CustomerController {

    private final CustomerRepository customerDetails = new CustomerRepository();

    @RequestMapping(value = "/api/customers", method = RequestMethod.GET)
    public ArrayList<CustomerDetails> selectOrderDetails() {
        return  customerDetails.selectAllCustomers();
    }

}
