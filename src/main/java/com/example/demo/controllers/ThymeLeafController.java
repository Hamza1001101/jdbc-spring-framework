package com.example.demo.controllers;

import com.example.demo.data_access.*;
import com.example.demo.models.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;




@Controller
public class ThymeLeafController {

    private final CustomerRepository customerRepository = new CustomerRepository();
    private final GenreRepository genreRepository = new GenreRepository();
    private final TrackRepository trackRepository = new TrackRepository();
    private final ArtistRepository artistRepository = new ArtistRepository();
    private final TrackInfoRepository trackInfoRepository = new TrackInfoRepository();

    @GetMapping("/")
    public String homePage(Model model) {


        model.addAttribute("genres", genreRepository.getRandomGenres());

        model.addAttribute("tracks", trackRepository.getRandomTracks());

        model.addAttribute("artists", artistRepository.getRandomArtists());

        model.addAttribute("customers", customerRepository.selectAllCustomers());
        return "index";
    }


    @RequestMapping(value = "/track/search", method = RequestMethod.GET)
    public String searchTrack() {
        return "search-track";
    }

    @RequestMapping(value = "/track/search", method = RequestMethod.POST)
    public String searchTrack(@RequestParam("searchTerm") String searchTerm, Model model) {
        System.out.println("searchTerm : " + searchTerm);


        model.addAttribute("success", true);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("trackInfo", trackInfoRepository.getTrackInfo(searchTerm));
        return "search-track";
    }


  /*  @RequestMapping(value = "/api/customers/add-customer", method = RequestMethod.GET)
    public String addCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "add-customers";
    }


    @RequestMapping(value = "/api/customers/add-customer", method = RequestMethod.POST)
    public String addCustomer(@ModelAttribute Customer customer, Model model) {
        boolean success = customerRepository.addCustomer(new Customer());
        model.addAttribute("success", success);
        if (success) {
            model.addAttribute("customer", customer);
        }
        return "add-customers";
    }*/
}
