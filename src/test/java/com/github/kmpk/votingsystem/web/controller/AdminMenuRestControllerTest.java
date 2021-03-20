package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.model.Menu;
import com.github.kmpk.votingsystem.repository.MenuRepository;
import com.github.kmpk.votingsystem.web.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static com.github.kmpk.votingsystem.MenuTestData.*;
import static com.github.kmpk.votingsystem.RestaurantTestData.REST_1_ID;
import static com.github.kmpk.votingsystem.RestaurantTestData.REST_2_ID;
import static com.github.kmpk.votingsystem.UserTestData.ADMIN;
import static com.github.kmpk.votingsystem.UserTestData.USER;
import static com.github.kmpk.votingsystem.web.TestUtil.readFromJsonResultActions;
import static com.github.kmpk.votingsystem.web.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminMenuRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminMenuRestController.REST_URL + '/';

    @Autowired
    private MenuRepository repository;

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + REST_1_ID + "/menus/" + MENU_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertMatch(repository.getAll(), MENU_2, MENU_3);
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + REST_2_ID + "/menus/" + MENU_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNotFound())
                .andExpect(hasInResponse("message"))
                .andExpect(hasInResponse("details"))
                .andDo(print());
    }

    @Test
    void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void testUpdate() throws Exception {
        Menu updated = new Menu(MENU_1);
        updated.setDate(LocalDate.MIN);
        mockMvc.perform(put(REST_URL + REST_1_ID + "/menus/" + updated.id())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(repository.get(MENU_1_ID, REST_1_ID), updated);
    }

    @Test
    void testUpdateNotFound() throws Exception {
        Menu updated = new Menu(MENU_1);
        updated.setDate(LocalDate.MIN);
        mockMvc.perform(put(REST_URL + REST_2_ID + "/menus/" + updated.id())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNotFound())
                .andExpect(hasInResponse("message"))
                .andExpect(hasInResponse("details"))
                .andDo(print());
    }

    @Test
    void testcreate() throws Exception {
        Menu created = new Menu(null, null, LocalDate.MIN, null);
        ResultActions action = mockMvc.perform(post(REST_URL + REST_1_ID + "/menus/")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated())
                .andDo(print());
        Menu returned = readFromJsonResultActions(action, Menu.class);
        created.setId(returned.getId());

        assertMatch(created, returned);
        assertMatch(repository.get(returned.getId(), REST_1_ID), created);
    }
}