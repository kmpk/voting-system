package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.model.Dish;
import com.github.kmpk.votingsystem.repository.DishRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(DishRestController.REST_URL)
public class DishRestController {
    private static final Logger logger = LoggerFactory.getLogger(DishRestController.class);

    static final String REST_URL = "/rest/menus";

    @Autowired
    private DishRepository repository;

    @GetMapping(path = "/{menuId}/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAllByMenu(@PathVariable("menuId") int menuId) {
        logger.info("get all dishes of menu with id={}", menuId);
        return repository.getAll(menuId);
    }

    @GetMapping(path = "/{menuId}/dishes/{dishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("dishId") int dishId, @PathVariable("menuId") int menuId) {
        logger.info("get dish with id={} with menuId={}", dishId, menuId);
        return repository.get(dishId,menuId);
    }
}
