package com.example.demo.controllers;

import com.example.demo.data_access.CustomerRepository;
import com.example.demo.data_access.GenreRepository;
import com.example.demo.models.Customer;
import com.example.demo.models.Genre;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
public class ThymeLeafController {

    private final CustomerRepository customerRepository = new CustomerRepository();
    private final GenreRepository genreRepository= new GenreRepository();

    @GetMapping("/")
    public String homePage(Model model) {

        ArrayList<Genre> genres = genreRepository.getRandomGenres();
        System.out.println(genreRepository.getMaxGenreId());
        model.addAttribute("genres", genres);



        model.addAttribute("customers", customerRepository.selectAllCustomers());
        return "index";
    }















    /**
     * Search customer
     *
     * @param firstName - customer first name
     * @param lastName  - customer last name
     * @param model     - model object.
     * @return - the customer info.
     */
  /*  @GetMapping("api/customers/customer")
    public String getCustomerByName(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName, Model model) {
        model.addAttribute("customer", customerRepository.selectCustomerByName(firstName, lastName));
        return "search-customer";
    }*/


   /* @RequestMapping(value = "/api/customers/customer-update/{id}", method = RequestMethod.PUT)
    public void updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        customerRepository.updateCustomer(id, customer);
    }*/


    /**
     * Add Customer to the database.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/api/customers/add-customer", method = RequestMethod.GET)
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "add-customers";
    }

   /* @RequestMapping(value = "/api/customers/add-customer", method = RequestMethod.POST)
    public String addCustomer(@ModelAttribute Customer customer, BindingResult error, Model model) {
        Boolean success = customerRepository.addCustomer(customer);
        model.addAttribute("success", success);
        if (success) {
            model.addAttribute("customer", new Customer());
        }
        return "add-customers";
    }
*/


 /*   @RequestMapping(value = "/api/customers", method = RequestMethod.GET)
    public ArrayList<Customer> selectOrderDetails() {
        return  customerRepository.selectAllCustomers();
    }

    @RequestMapping(value = "api/customers", method = RequestMethod.GET)
    public Customer getCustomerByQueryId(@RequestParam(value = "queryId", defaultValue ="ALFKI") String queryId) {
      return customerRepository.selectCustomerById(queryId);
    }*/


      /*@RequestMapping(value = "api/customer", method = RequestMethod.GET)
    public Customer getCustomerByQueryId(@RequestParam(value="id", defaultValue = "ALFKI") String id){
        //System.out.println(customerRepository.selectCustomerById(id).getFirstName());
        return  customerRepository.selectCustomerById(id);
    }

  public static String printOrders(ArrayList<Customer> orderDetails) {
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
