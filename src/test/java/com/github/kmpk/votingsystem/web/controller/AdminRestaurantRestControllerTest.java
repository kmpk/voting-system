package com.github.kmpk.votingsystem.web.controller;

import com.github.kmpk.votingsystem.model.Restaurant;
import com.github.kmpk.votingsystem.repository.RestaurantRepository;
import com.github.kmpk.votingsystem.web.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.github.kmpk.votingsystem.RestaurantTestData.*;
import static com.github.kmpk.votingsystem.UserTestData.ADMIN;
import static com.github.kmpk.votingsystem.UserTestData.USER;
import static com.github.kmpk.votingsystem.web.TestUtil.readFromJsonResultActions;
import static com.github.kmpk.votingsystem.web.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestaurantRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminRestaurantRestController.REST_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + REST_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertMatch(repository.findAll(), REST_2, REST_3);
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 0)
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
        Restaurant updated = new Restaurant(REST_1);
        updated.setName("Updated restaurant name 1");
        updated.setAddress("Updated restaurant address 1");
        updated.setDescription("Updated restaurant description 1");
        mockMvc.perform(put(REST_URL + updated.id())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(repository.get(REST_1_ID), updated);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testUpdateDuplicate() throws Exception {
        Restaurant updated = new Restaurant(REST_1);
        updated.setAddress(REST_2.getAddress());
        mockMvc.perform(put(REST_URL + updated.id())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict())
                .andExpect(hasInResponse("message"))
                .andExpect(hasInResponse("details"))
                .andDo(print());
    }

    @Test
    void testUpdateNotFound() throws Exception {
        Restaurant updated = new Restaurant(REST_1);
        updated.setAddress("New adddress");
        updated.setId(0);
        mockMvc.perform(put(REST_URL + 0)
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
        Restaurant created = new Restaurant(null, "new restaurant", "new address", "new description");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated())
                .andDo(print());
        Restaurant returned = readFromJsonResultActions(action, Restaurant.class);
        created.setId(returned.getId());

        assertMatch(created, returned);
        assertMatch(repository.get(returned.getId()), created);
    }
}