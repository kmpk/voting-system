package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.model.Dish;
import com.github.kmpk.votingsystem.repository.DishRepository;
import com.github.kmpk.votingsystem.web.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.github.kmpk.votingsystem.DishTestData.*;
import static com.github.kmpk.votingsystem.MenuTestData.MENU_1_ID;
import static com.github.kmpk.votingsystem.MenuTestData.MENU_2_ID;
import static com.github.kmpk.votingsystem.UserTestData.ADMIN;
import static com.github.kmpk.votingsystem.UserTestData.USER;
import static com.github.kmpk.votingsystem.web.TestUtil.readFromJsonResultActions;
import static com.github.kmpk.votingsystem.web.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminDishRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminDishRestController.REST_URL + '/';

    @Autowired
    DishRepository repository;

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MENU_1_ID + "/dishes/" + DISH_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertMatch(repository.getAll(MENU_1_ID), DISH_2, DISH_3);
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + MENU_2_ID + "/dishes/" + DISH_1_ID)
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
        Dish updated = new Dish(DISH_1);
        updated.setName("new name");
        updated.setPrice(999);
        mockMvc.perform(put(REST_URL + MENU_1_ID + "/dishes/" + updated.id())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(repository.get(updated.getId(), MENU_1_ID), updated);
    }

    @Test
    void testUpdateNotFound() throws Exception {
        Dish updated = new Dish(DISH_1);
        updated.setName("new name");
        updated.setPrice(999);
        mockMvc.perform(put(REST_URL + MENU_2_ID + "/dishes/" + updated.id())
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
        Dish[] newDishes = {
                new Dish(null, "new 1", 1000, null),
                new Dish(null, "new 2", 2000, null)};
        ResultActions action = mockMvc.perform(post(REST_URL + MENU_1_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(newDishes)))
                .andExpect(status().isCreated())
                .andDo(print());
        Dish[] returned = readFromJsonResultActions(action, Dish[].class);
        for (int i = 0; i < returned.length; i++) {
            newDishes[i].setId(returned[i].getId());
        }

        assertMatch(List.of(returned), newDishes);
        assertMatch(repository.getAll(MENU_1_ID),
                Stream.concat(Stream.of(DISH_1, DISH_2, DISH_3), Arrays.stream(returned))
                        .toArray(Dish[]::new));
    }
}