package com.github.kmpk.votingsystem;

import com.github.kmpk.votingsystem.model.Menu;
import com.github.kmpk.votingsystem.web.TestUtil;
import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.List;

import static com.github.kmpk.votingsystem.RestaurantTestData.*;
import static com.github.kmpk.votingsystem.model.AbstractBaseEntity.START_SEQ;
import static com.github.kmpk.votingsystem.web.JsonUtil.readListFromJsonMvcResult;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuTestData {
    public static final int MENU_1_ID = START_SEQ + 5;
    public static final int MENU_2_ID = START_SEQ + 6;
    public static final int MENU_3_ID = START_SEQ + 7;

    public static final Menu MENU_1 = new Menu(MENU_1_ID, REST_1, LocalDate.of(2000, 1, 1), null);
    public static final Menu MENU_2 = new Menu(MENU_2_ID, REST_2, LocalDate.of(2000, 1, 1), null);
    public static final Menu MENU_3 = new Menu(MENU_3_ID, REST_3, LocalDate.of(2000, 1, 1), null);

    public static void assertMatch(Menu actual, Menu expected) {
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("restaurant","dishes")
                .isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher getMenuMatcher(Menu expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, Menu.class), expected);
    }

    public static ResultMatcher getMenuMatcher(Menu... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Menu.class), List.of(expected));
    }
}
