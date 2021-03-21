package com.github.kmpk.votingsystem;

import com.github.kmpk.votingsystem.model.Dish;
import com.github.kmpk.votingsystem.web.TestUtil;
import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static com.github.kmpk.votingsystem.model.AbstractBaseEntity.START_SEQ;
import static com.github.kmpk.votingsystem.web.JsonUtil.readListFromJsonMvcResult;
import static org.assertj.core.api.Assertions.assertThat;

public class DishTestData {
    public static final int DISH_1_ID = START_SEQ + 8;
    public static final int DISH_2_ID = START_SEQ + 9;
    public static final int DISH_3_ID = START_SEQ + 10;

    public static final Dish DISH_1 = new Dish(DISH_1_ID, "Dish name 1", 10000, null);
    public static final Dish DISH_2 = new Dish(DISH_2_ID, "Dish name 2", 20000, null);
    public static final Dish DISH_3 = new Dish(DISH_3_ID, "Dish name 3", 30000, null);

    public static void assertMatch(Dish actual, Dish expected) {
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("menu")
                .isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher getDishMatcher(Dish expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, Dish.class), expected);
    }

    public static ResultMatcher getDishMatcher(Dish... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Dish.class), List.of(expected));
    }
}
