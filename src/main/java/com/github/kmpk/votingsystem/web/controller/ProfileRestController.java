package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.AuthorizedUser;
import com.github.kmpk.votingsystem.model.User;
import com.github.kmpk.votingsystem.service.UserService;
import com.github.kmpk.votingsystem.to.UserTo;
import com.github.kmpk.votingsystem.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController()
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileRestController.class);

    static final String REST_URL = "/rest/profile";

    @Autowired
    private UserService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
        logger.info("get by user {}", authUser.getId());
        return service.get(authUser.getId());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        logger.info("delete by user {}", authUser.getId());
        service.delete(authUser.getId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        logger.info("update by user {}", authUser.getId());
        ValidationUtil.assureIdConsistent(userTo, authUser.getId());
        service.update(userTo);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        logger.info("register new user {}", userTo.getEmail());
        User created = service.create(new User(userTo.getName(), userTo.getEmail(), userTo.getPassword()));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
