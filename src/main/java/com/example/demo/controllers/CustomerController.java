package com.example.demo.controllers;

import com.example.demo.data_access.CustomerRepository;
import com.example.demo.models.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class CustomerController {

    private final CustomerRepository customerRepository = new CustomerRepository();




    @GetMapping("/")
    public String getPeople(Model model) {
        List<Customer> customers = new ArrayList<>();
        model.addAttribute("customers", customers);
        return "index";
    }

    /**
     * Fetching all the customers in the database.
     * @return - All the customers.
     */
    @RequestMapping(value = "/api/customers", method = RequestMethod.GET)
    public ArrayList<Customer> selectOrderDetails() {
        return  customerRepository.selectAllCustomers();
    }

    /*@RequestMapping(value = "api/customers", method = RequestMethod.GET)
    public Customer getCustomerByQueryId(@RequestParam(value = "queryId", defaultValue ="ALFKI") String queryId) {
      return customerRepository.selectCustomerById(queryId);
    }*/


    @RequestMapping(value = "api/customer", method = RequestMethod.GET)
    public Customer getCustomerByQueryId(@RequestParam(value="id", defaultValue = "ALFKI") String id){
        //System.out.println(customerRepository.selectCustomerById(id).getFirstName());
        return  customerRepository.selectCustomerById(id);
    }

   /* public static String printOrders(ArrayList<Customer> orderDetails) {
        if (orderDetails.size() != 0) {
            for (Customer c : orderDetails) {
                System.out.println("-----------------------------");
                System.out.println(c.getCustomerId());
                System.out.println(c.getCountry());
                System.out.println(c.getFirstName());
            }
        } else {
            System.out.println("No customers returned");
        }
        return null;
    }*/
}
