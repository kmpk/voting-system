package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.model.Menu;
import com.github.kmpk.votingsystem.repository.MenuRepository;
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

@RestController
@RequestMapping(AdminMenuRestController.REST_URL)
public class AdminMenuRestController {
    private static final Logger logger = LoggerFactory.getLogger(AdminMenuRestController.class);

    static final String REST_URL = "/rest/admin/restaurants";

    @Autowired
    private MenuRepository repository;

    @PostMapping(path = "/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@Validated @RequestBody Menu menu, @PathVariable("restaurantId") int restaurantId) {
        logger.info("create new menu with restaurantId={}", restaurantId);
        Menu created = repository.create(menu, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + restaurantId + "/menus/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(path = "/{restaurantId}/menus/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Menu menu, @PathVariable("restaurantId") int restaurantId, @PathVariable("id") int id) {
        logger.info("update menu with restaurantID={}", restaurantId);
        ValidationUtil.assureIdConsistent(menu, id);
        repository.update(menu, restaurantId);
    }

    @DeleteMapping(path = "/{restaurantId}/menus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable("id") int id) {
        logger.info("delete menu with id={} and restaurantId={}", id, restaurantId);
        repository.delete(id, restaurantId);
    }
}
