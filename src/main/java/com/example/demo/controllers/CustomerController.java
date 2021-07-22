package com.example.demo.controllers;

import com.example.demo.data_access.CustomerRepository;
import com.example.demo.models.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class CustomerController {

    private final CustomerRepository customerRepository = new CustomerRepository();


    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/api/customers")
    public String getPeople(Model model) {
        model.addAttribute("customers", customerRepository.selectAllCustomers());
        return "view-customers";
    }


    // @GetMapping("api/customers/countries")
    @RequestMapping(value = "/api/customers/countries", method = RequestMethod.GET)
    public String getCustomerByCountry(Model model) {
        model.addAttribute("countries", customerRepository.selectCustomersByCountry());
        return "view-customers-per-country";
    }


    @GetMapping(value = "/api/customers/{id}")
    public String getSpecificCustomer(@PathVariable String id, Model model) {
        model.addAttribute("customer", customerRepository.selectCustomerById(id));
        return "view-customer-by-id";
    }


    /**
     * Search customer
     *
     * @param firstName - customer first name
     * @param lastName  - customer last name
     * @param model     - model object.
     * @return - the customer info.
     */
    @GetMapping("api/customers/customer")
    public String getCustomerByName(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName, Model model) {
        model.addAttribute("customers", customerRepository.selectCustomerByName(firstName, lastName));
        return "search-customer";
    }


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

    @RequestMapping(value = "/api/customers/add-customer", method = RequestMethod.POST)
    public String addCustomer(@ModelAttribute Customer customer, BindingResult error, Model model) {
        Boolean success = customerRepository.addCustomer(customer);
        model.addAttribute("success", success);
        if (success) {
            model.addAttribute("customer", new Customer());
        }
        return "add-customers";
    }



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
