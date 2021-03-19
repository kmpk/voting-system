package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.model.Restaurant;
import com.github.kmpk.votingsystem.repository.RestaurantRepository;
import com.github.kmpk.votingsystem.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController()
@RequestMapping(AdminRestaurantRestController.REST_URL)
public class AdminRestaurantRestController {
    private static final Logger logger = LoggerFactory.getLogger(AdminRestaurantRestController.class);

    @Autowired
    RestaurantRepository repository;

    static final String REST_URL = "/rest/admin/restaurants";

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        logger.info("delete user with id={}", id);
        repository.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        logger.info("update user with id={}", id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        repository.update(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        logger.info("create new restaurant");
        Restaurant created = repository.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
