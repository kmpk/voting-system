package com.github.kmpk.votingsystem.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.github.kmpk.votingsystem.RestaurantTestData.*;
import static com.github.kmpk.votingsystem.UserTestData.USER;
import static com.github.kmpk.votingsystem.web.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantRestController.REST_URL + '/';

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRestaurantMatcher(REST_1, REST_2, REST_3));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + REST_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRestaurantMatcher(REST_1));
    }

    @Test
    void testNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 0)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}