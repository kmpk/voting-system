package com.github.kmpk.votingsystem;

import com.github.kmpk.votingsystem.model.Restaurant;
import com.github.kmpk.votingsystem.web.TestUtil;
import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static com.github.kmpk.votingsystem.model.AbstractBaseEntity.START_SEQ;
import static com.github.kmpk.votingsystem.web.JsonUtil.readListFromJsonMvcResult;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final int REST_1_ID = START_SEQ + 2;
    public static final int REST_2_ID = START_SEQ + 3;
    public static final int REST_3_ID = START_SEQ + 4;


    public static final Restaurant REST_1 = new Restaurant(REST_1_ID, "Restaurant name 1", "Restaurant address 1", "Restaurant description 1");
    public static final Restaurant REST_2 = new Restaurant(REST_2_ID, "Restaurant name 2", "Restaurant address 2", "Restaurant description 2");
    public static final Restaurant REST_3 = new Restaurant(REST_3_ID, "Restaurant name 3", "Restaurant address 3", "Restaurant description 3");

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("menus")
                .isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("menus")
                .isEqualTo(expected);
    }

    public static ResultMatcher getRestaurantMatcher(Restaurant expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher getRestaurantMatcher(Restaurant... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }
}
