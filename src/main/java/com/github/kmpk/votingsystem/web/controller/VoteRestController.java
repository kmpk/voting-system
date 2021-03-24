package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.AuthorizedUser;
import com.github.kmpk.votingsystem.model.Vote;
import com.github.kmpk.votingsystem.service.VoteService;
import com.github.kmpk.votingsystem.to.VotesCountTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController()
@RequestMapping(VoteRestController.REST_URL)
public class VoteRestController {
    private static final Logger logger = LoggerFactory.getLogger(VoteRestController.class);

    static final String REST_URL = "/rest/votes";

    @Autowired
    VoteService service;

    @GetMapping(path = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAllByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        logger.info("get all votes by date={}", localDate);
        return service.getAllByDate(localDate);
    }

    @PutMapping(path = "/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> vote(@AuthenticationPrincipal AuthorizedUser authUser, @PathVariable("restaurantId") int restaurantId) {
        logger.info("vote for restaurant with id={} by user with id={}", authUser.getId(), restaurantId);
        Vote created = service.add(restaurantId, authUser.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + "{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable("id") int id) {
        logger.info("get vote with id={}", id);
        return service.get(id);
    }

    @GetMapping(path = "/result/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VotesCountTo> getVotingResultByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        logger.info("get voting result by date={}", localDate);
        return service.getResult(localDate);
    }
}
