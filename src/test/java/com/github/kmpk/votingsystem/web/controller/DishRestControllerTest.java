package com.github.kmpk.votingsystem.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.github.kmpk.votingsystem.DishTestData.*;
import static com.github.kmpk.votingsystem.MenuTestData.MENU_1_ID;
import static com.github.kmpk.votingsystem.MenuTestData.MENU_2_ID;
import static com.github.kmpk.votingsystem.UserTestData.USER;
import static com.github.kmpk.votingsystem.web.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = DishRestController.REST_URL + '/';

    @Test
    void testGetAllByMenu() throws Exception {
        mockMvc.perform(get(REST_URL + MENU_1_ID + "/dishes")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DISH_1, DISH_2, DISH_3))
                .andDo(print());
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MENU_1_ID + "/dishes/" + DISH_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DISH_1))
                .andDo(print());
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + MENU_2_ID + "/dishes/" + DISH_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(hasInResponse("message"))
                .andExpect(hasInResponse("details"))
                .andDo(print());
    }
}