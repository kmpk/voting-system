package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.model.Dish;
import com.github.kmpk.votingsystem.repository.DishRepository;
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
import java.util.List;

@RestController
@RequestMapping(AdminDishRestController.REST_URL)
public class AdminDishRestController {
    private static final Logger logger = LoggerFactory.getLogger(AdminDishRestController.class);

    static final String REST_URL = "/rest/admin/menus";

    @Autowired
    private DishRepository repository;

    @PostMapping(path = "/{menuId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Dish>> create(@PathVariable("menuId") int menuId, @Validated @RequestBody Dish[] dishes) {
        logger.info("create new dish with menuId={}", menuId);
        List<Dish> created = repository.create(menuId, dishes);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DishRestController.REST_URL + "/" + menuId + "/dishes")
                .buildAndExpand(created.get(0).getMenu().getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{menuId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Dish dish, @PathVariable("menuId") int menuId, @PathVariable("id") int id) {
        logger.info("update dish with menuId={} and id={}", menuId, id);
        ValidationUtil.assureIdConsistent(dish, id);
        repository.update(dish, menuId);
    }

    @DeleteMapping(path = "/{menuId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("menuId") int menuId, @PathVariable("id") int id) {
        logger.info("delete dish with menuId={} and id={}", menuId, id);
        repository.delete(id, menuId);
    }
}
