package com.example.demo.controllers;

import com.example.demo.data_access.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ThymeLeafController {

    private final CustomerRepository customerRepository = new CustomerRepository();
    private final GenreRepository genreRepository = new GenreRepository();
    private final TrackRepository trackRepository = new TrackRepository();
    private final ArtistRepository artistRepository = new ArtistRepository();
    private final TrackInfoRepository trackInfoRepository = new TrackInfoRepository();

    @GetMapping("/")
    public String homePage(Model model) {

        model.addAttribute("genres", genreRepository.generateRandomGenres());
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

        model.addAttribute("success", true);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("trackInfo", trackInfoRepository.getTrackInfo(searchTerm));
        return "search-track";
    }

}
