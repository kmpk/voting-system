package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.AuthorizedUser;
import com.github.kmpk.votingsystem.model.User;
import com.github.kmpk.votingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(AdminRestController.REST_URL)
public class AdminRestController {
    static final String REST_URL = "/rest/admin/users";

    @Autowired
    private UserService service;

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthorizedUser authUser,@PathVariable("id") int id) {
        return service.get(id);
    }
}
