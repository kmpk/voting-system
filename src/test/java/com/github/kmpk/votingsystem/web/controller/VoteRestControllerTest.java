package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.model.Vote;
import com.github.kmpk.votingsystem.repository.VoteRepository;
import com.github.kmpk.votingsystem.service.VoteService;
import com.github.kmpk.votingsystem.to.VotesCountTo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.github.kmpk.votingsystem.RestaurantTestData.REST_1;
import static com.github.kmpk.votingsystem.RestaurantTestData.REST_1_ID;
import static com.github.kmpk.votingsystem.UserTestData.USER;
import static com.github.kmpk.votingsystem.VoteTestData.*;
import static com.github.kmpk.votingsystem.web.TestUtil.readFromJsonResultActions;
import static com.github.kmpk.votingsystem.web.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    private VoteService service;
    @Autowired
    private VoteRepository repository;

    @AfterEach
    void resetClock() {
        service.setClock(Clock.systemDefaultZone());
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE_1))
                .andDo(print());
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 0)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(hasInResponse("message"))
                .andExpect(hasInResponse("details"))
                .andDo(print());
    }

    @Test
    void testGetByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "/by")
                .param("date", VOTE_1.getDateTime().toLocalDate().toString())
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getVoteMatcher(VOTE_3, VOTE_2, VOTE_1, VOTE_4))
                .andDo(print());
    }

    @Test
    void testVote() throws Exception {
        //set service clock to 10:00 to not depend on the real time of the test
        service.setClock(Clock.fixed(LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .plus(10, ChronoUnit.HOURS)
                .toInstant(), ZoneId.systemDefault()));

        Vote expected = new Vote(null, USER, REST_1, null);
        ResultActions resultActions = mockMvc.perform(put(REST_URL + REST_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
        Vote returned = readFromJsonResultActions(resultActions, Vote.class);
        expected.setId(returned.getId());
        expected.setDateTime(returned.getDateTime());

        assertMatch(returned, expected);
        assertMatch(returned, repository.getOne(expected.getId()));
    }

    @Test
    void testVoteChangeIllegalTime() throws Exception {
        service.setClock(Clock.fixed(LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .plus(10, ChronoUnit.HOURS)
                .toInstant(), ZoneId.systemDefault()));

        Vote expected = new Vote(null, USER, REST_1, null);
        ResultActions resultActions = mockMvc.perform(put(REST_URL + REST_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
        Vote returned = readFromJsonResultActions(resultActions, Vote.class);
        expected.setId(returned.getId());
        expected.setDateTime(returned.getDateTime());

        assertMatch(returned, expected);
        assertMatch(returned, repository.getOne(expected.getId()));

        service.setClock(Clock.fixed(LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .plus(12, ChronoUnit.HOURS)
                .toInstant(), ZoneId.systemDefault()));

        mockMvc.perform(put(REST_URL + REST_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isConflict())
                .andExpect(hasInResponse("message"))
                .andExpect(hasInResponse("details"))
                .andDo(print());
    }

    @Test
    void testVoteCount() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(REST_URL + "/result/by")
                .param("date", VOTE_1.getDateTime().toLocalDate().toString())
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
        VotesCountTo[] returned = readFromJsonResultActions(resultActions, VotesCountTo[].class);
        assertCountsMatch(List.of(returned), List.of(VOTES_COUNT_1, VOTES_COUNT_2));
    }
}