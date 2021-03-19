package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.model.User;
import com.github.kmpk.votingsystem.service.UserService;
import com.github.kmpk.votingsystem.to.UserAdminTo;
import com.github.kmpk.votingsystem.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(AdminRestController.REST_URL)
public class AdminRestController {
    private static final Logger logger = LoggerFactory.getLogger(AdminRestController.class);

    static final String REST_URL = "/rest/admin/users";

    @Autowired
    private UserService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        logger.info("get all users");
        return service.getAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        logger.info("get user with id={}", id);
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        logger.info("delete user with id={}", id);
        service.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody UserAdminTo userTo, @PathVariable("id") int id) {
        logger.info("update user with id={}", id);
        ValidationUtil.assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getByMail(@RequestParam("email") String email) {
        logger.info("get user with email={}", email);
        return service.getByEmail(email);
    }
}
