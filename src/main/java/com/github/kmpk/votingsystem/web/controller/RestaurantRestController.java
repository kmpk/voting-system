package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.exception.EntityNotFoundException;
import com.github.kmpk.votingsystem.model.Restaurant;
import com.github.kmpk.votingsystem.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantRestController.class);

    static final String REST_URL = "/rest/restaurants";

    @Autowired
    private RestaurantRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        logger.info("get all restaurants");
        return repository.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        logger.info("get restaurant with id={}", id);
        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Restaurant with  id=%d is not found", id)));
    }
}
