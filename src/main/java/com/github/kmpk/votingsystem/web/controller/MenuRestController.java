package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.model.Menu;
import com.github.kmpk.votingsystem.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.github.kmpk.votingsystem.web.controller.MenuRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class MenuRestController {
    private static final Logger logger = LoggerFactory.getLogger(MenuRestController.class);

    static final String REST_URL = "/rest/restaurants";

    @Autowired
    private MenuRepository repository;

    @GetMapping(path = "/{restaurantId}/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAllByRestaurant(@PathVariable("restaurantId") int restaurantId) {
        logger.info("get all menus by restaurantId={}",restaurantId);
        return repository.getAllByRestaurantId(restaurantId);
    }

    @GetMapping(path = "/{restaurantId}/menus/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("restaurantId") int restaurantId, @PathVariable("id") int id) {
        logger.info("get menu with id={}", id);
        return repository.get(id, restaurantId);
    }

    @GetMapping(path = "/menus/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAllByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        logger.info("get menus by date {}", date);
        return repository.getAllByDate(date);
    }
}
