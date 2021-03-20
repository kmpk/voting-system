package com.github.kmpk.votingsystem.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.github.kmpk.votingsystem.MenuTestData.*;
import static com.github.kmpk.votingsystem.RestaurantTestData.REST_1_ID;
import static com.github.kmpk.votingsystem.RestaurantTestData.REST_3_ID;
import static com.github.kmpk.votingsystem.UserTestData.USER;
import static com.github.kmpk.votingsystem.web.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuRestController.REST_URL + '/';

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL+"/menus/")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getMenuMatcher(MENU_1, MENU_2, MENU_3))
                .andDo(print());
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + REST_1_ID + "/menus/" + MENU_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getMenuMatcher(MENU_1))
                .andDo(print());
    }

    @Test
    void testNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + REST_3_ID + "/menus/" + MENU_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}